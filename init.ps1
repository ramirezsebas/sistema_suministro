if ($args.Count -ne 2) {
    Write-Error "Usage: init.ps1 <protocol> <type(client or server)>"
    exit 1
}

$protocolo = $args[0].ToUpper()
$tipo = $args[1].ToUpper()

if ($protocolo -ne "TCP" -and $protocolo -ne "UDP") {
    Write-Error "Protocol must be either TCP or UDP"
    exit 1
}

if ($tipo -ne "CLIENT" -and $tipo -ne "SERVER") {
    Write-Error "Type must be either client or server"
    exit 1
}

if ($tipo -eq "CLIENT") {
    cd client
}
if($tipo -eq "SERVER") {
    cd server
}




if ($protocolo -eq "TCP") {
    echo "TCP"
    mvn clean install -U
    mvn clean package
    java -jar target\sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar tcp
}
if($protocolo -eq "UDP"){
    echo "UDP"
    mvn clean install -U
    mvn clean package
    java -jar target\sistema-suministro-1.0-SNAPSHOT-jar-with-dependencies.jar udp
}

cd ../