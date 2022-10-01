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

if($protocol -eq "TCP"){
    if($tipo -eq "CLIENT"){
        $xml = [xml](Get-Content pom-docker.xml)
        $xml.project.build.pluginManagement.plugins.plugin.configuration.archive.manifest.mainClass = "py.edu.fpuna.distri.tp_sockets.tcp.AppClientTCP"
        $xml.Save("pom-docker.xml")
    }else{
        $xml = [xml](Get-Content pom-docker.xml)
        $xml.project.build.pluginManagement.plugins.plugin.configuration.archive.manifest.mainClass = "py.edu.fpuna.distri.tp_sockets.tcp.AppServerTCP"
        $xml.Save("pom-docker.xml")
    }
}else{
    if($tipo -eq "CLIENT"){
        $xml = [xml](Get-Content pom-docker.xml)
        $xml.project.build.pluginManagement.plugins.plugin.configuration.archive.manifest.mainClass = "py.edu.fpuna.distri.tp_sockets.udp.AppClientUDP"
        $xml.Save("pom-docker.xml")
    }else{
        $xml = [xml](Get-Content pom-docker.xml)
        $xml.project.build.pluginManagement.plugins.plugin.configuration.archive.manifest.mainClass = "py.edu.fpuna.distri.tp_sockets.udp.AppServerUDP"
        $xml.Save("pom-docker.xml")
    }
}


mvn clean install -U
mvn clean package
java -jar target/sistema-suministro-1.0-SNAPSHOT.jar