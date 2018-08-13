kubectl exec -ti mysql-0 --bash mysql -u root -p123456 -e "CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;"
kubectl exec -ti mysql-0 --bash mysql -u root -p123456 <<QUERY_INPUT
use kafka;
CREATE TABLE measurements (probeId INT(20), resourceId INT(20), type VARCHAR(20), descriptionId INT(20), time INT(20), value INT(20));
QUERY_INPUT
