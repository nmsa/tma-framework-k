timestamp() {
	date +'%F %T'
}


i="1"

while [ $i -lt 141 ]
do
time=\'$(timestamp)\'
kubectl exec -ti mysql-0 -- mysql -u root -ppassword <<QUERY_INPUT
use teste;
INSERT INTO Probe(probeId,probeName,password,salt,token,tokenExpiration) VALUES ($i,"probe wildfly WSVD", "a","a","a",${time});
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES ($i,"VM_VIRT_NODE", "a","a");
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES ($i,"CPU_Usage", "measurement","Mi");
QUERY_INPUT
i=$((i+1))
done
