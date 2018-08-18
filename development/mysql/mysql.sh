kubectl exec -ti mysql-0 -- mysql -u root -ppassword -e "CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;"
kubectl exec -ti mysql-0 -- mysql -u root -ppassword <<QUERY_INPUT
use knowledge;
CREATE TABLE measurements (probeId INT(20), resourceId INT(20), type VARCHAR(20), descriptionId INT(20), time INT(20), value FLOAT(20));
QUERY_INPUT
