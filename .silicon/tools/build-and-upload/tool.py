from rcon.source import Client
import paramiko
import json
import os

config_file = "build-and-upload-config.json"


def read_json(file: str) -> any:
    fp = open(file, encoding="utf-8")
    data = json.load(fp)
    fp.close()
    return data


def write_json(file: str, data: any) -> None:
    fp = open(file, "wt", encoding="utf-8")
    json.dump(data, fp, indent=4)
    fp.close()


if not os.path.exists(config_file):
    write_json(config_file, {
        "rcon": {
            "enabled": False,
            "ip": "0.0.0.0",
            "port": "7777",
            "password": "1234"
        },
        "sftp": {
            "enabled": False,
            "ip": "0.0.0.0",
            "port": "22",
            "user": "user",
            "password": "1234",
            "local_file": "build/libs/PLUGIN.jar",
            "server_file": "server/plugins/PLUGIN.jar"
        }
    })
    print("Конфигурация не обнаружена. Файл конфигурации создан, заполните его.")
    exit(0)

config = read_json(config_file)

os.system("gradlew jar")

if config["rcon"]["enabled"]:
    try:
        with Client(config["rcon"]["ip"], config["rcon"]["port"], passwd=config["rcon"]["password"]) as client:
            response = client.run('stop')
    except:
        pass

    print("Сервер остановлен")


def create_sftp_client(host, port, username, password):
    transport = paramiko.Transport((host, port))
    transport.connect(username=username, password=password)

    sftp_client = paramiko.SFTPClient.from_transport(transport)

    return sftp_client


def upload_file_to_server(sftp_client, local_file, remote_file):
    sftp_client.put(local_file, remote_file)


if config["sftp"]["enabled"]:
    sftp_client = create_sftp_client(
        config["sftp"]["ip"],
        int(config["sftp"]["port"]),
        config["sftp"]["user"],
        config["sftp"]["password"]
    )
    upload_file_to_server(sftp_client, config["sftp"]["local_file"], config["sftp"]["server_file"])

    print("Плагин загружен")
