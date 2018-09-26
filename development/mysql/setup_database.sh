# Deploy MySQL in Kubernetes Cluster
kubectl create -f mysql-deployment.yaml

# Timeout until MySQL pod is ready
getmyqlstate () {
	mysqlstate=$(kubectl get pods -n default mysql-0 -o jsonpath="{.status.phase}")
}
getmyqlstate
while [ $mysqlstate != "Running" ]
do
getmyqlstate
sleep 1
done

# Creation of knowledge in MySQL pod
kubectl exec -ti mysql-0 -- mysql -u root -ppassword -e "CREATE DATABASE knowledge /*\!40100 DEFAULT CHARACTER SET utf8 */;"

# knowledge database population
kubectl exec -ti mysql-0 -- mysql -u root -ppassword knowledge < ../../database/TMA-K_create_database.sql
