#Install ceph
apt-get -y install ceph

#Install docker
apt-get -y install docker.io

#Pull ceph Docker image from Docker Hub
docker pull docker.avvo.com/ceph-demo:luminous

#Remove some conflicting files 
rm -rf /etc/ceph/*
rm -rf /var/lib/ceph/*

# Give permissions to container can write in necessary directories
chmod 777 /etc/ceph
chmod 777 /var/lib/ceph

# Deploy Ceph container
docker run --net=host -v /etc/ceph:/etc/ceph -v /var/lib/ceph:/var/lib/ceph \
  -e MON_IP=192.168.1.1 -e CEPH_PUBLIC_NETWORK=192.168.1.0/24 docker.avvo.com/ceph-demo:luminous
