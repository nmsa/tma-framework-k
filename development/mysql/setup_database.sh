# Deploy secret with MySQL password
kubectl create secret generic mysql-pass --from-literal=password=passtobereplaced

# Deploy MySQL in Kubernetes Cluster
kubectl create -f mysql-deployment.yaml

# Timeout until MySQL pod is ready
getMyqlState () {
	mysqlstate=$(kubectl get pods -n default mysql-0 -o jsonpath="{.status.phase}")
}
getMyqlState
while [ $mysqlstate != "Running" ]
do
getMyqlState
sleep 1
done

# Creation of knowledge in MySQL pod
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD -e \"CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;\""

# knowledge database population
kubectl exec -ti mysql-0 -- bash -c "mysql -u root --password=\$MYSQL_ROOT_PASSWORD knowledge < /mysql/TMA-K_create_database.sql"
