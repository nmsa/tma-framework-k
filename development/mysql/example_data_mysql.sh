timestamp() {
	date +'%F %T'
}


i="1"

while [ $i -lt 141 ]
do
time=\'$(timestamp)\'
kubectl exec -ti mysql-0 -- mysql -u root -ppassword <<QUERY_INPUT
use knowledge;
INSERT INTO Probe(probeId,probeName,password,salt,token,tokenExpiration) VALUES ($i,"probe Wildfly WSVD", "n/a","n/a","n/a",${time});
INSERT INTO Description(descriptionId,dataType,descriptionName,unit) VALUES ($i,"CPU_Usage", "measurement","Mi");
INSERT INTO Resource(resourceId,resourceName,resourceType,resourceAddress) VALUES ($i,"VM_VIRT_NODE", "VM","n/a");
QUERY_INPUT
i=$((i+1))
done
