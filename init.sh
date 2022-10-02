if [ $# -lt 2 ]; then
    echo "Uso: $0 <protocolo> <cliente o servidor> ..."
    exit 1
fi

protocolo=$(echo $1 | tr '[:lower:]' '[:upper:]')

if [ $protocolo != "TCP" ] && [ $protocolo != "UDP" ]; then
    echo "Protocolo invalido"
    exit 1
fi

tipo=$(echo $2 | tr '[:lower:]' '[:upper:]')

if [ $tipo != "CLIENTE" ] && [ $tipo != "SERVIDOR" ]; then
    echo "Tipo invalido"
    exit 1
fi


# if its client navigate to client folder
if [ $tipo = "CLIENTE" ]; then
    cd cliente
else
    cd servidor
fi
   


# if its tcp execute tcp client
if [ $protocolo = "TCP" ]; then
    mvn clean install -U
    mvn clean package
    java -jar target\sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar tcp
else
    mvn clean install -U
    mvn clean package
    java -jar target\sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar udp
fi
