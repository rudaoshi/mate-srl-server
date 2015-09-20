
set -eux

srl_jarfile=srl.jar
server_jarfile=mate-srl-server.jar
rm -rf _build $srl_jarfile $server_jarfile
mkdir _build

javac -d _build -cp "$(ls  ../lib/*.jar| tr '\n' ':')" $(find ../src/se/ | grep java)
(cd _build && jar cf ../$srl_jarfile .)
ls -l $srl_jarfile

javac -d _build -cp "$(ls  ../lib/*.jar| tr '\n' ':')":srl.jar $(find ../src/so/ | grep java)
(cd _build && jar cf ../$server_jarfile .)
ls -l $server_jarfile

rm -rf _build