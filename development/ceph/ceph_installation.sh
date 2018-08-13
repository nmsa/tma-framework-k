apt-get -y install ceph
apt-get -y install docker.io
docker pull ceph/demo
rm -rf /etc/ceph/*
rm -rf /var/lib/ceph/*
chmod 777 /etc/ceph
chmod 777 /var/lib/ceph
docker run --net=host -v /etc/ceph:/etc/ceph -v /var/lib/ceph:/var/lib/ceph \
  -e MON_IP=192.168.1.3 -e CEPH_PUBLIC_NETWORK=192.168.1.0/24 ceph/demo
rbd create db_mysql -s 1024
rbd feature disable db_mysql fast-diff
rbd feature disable db_mysql object-map
rbd feature disable db_mysql deep-flatten
mkfs.ext4 /dev/rbd0
ceph auth get-key client.admin > temp.txt
key="$(sed -n 1p temp.txt)"
echo "${key}"| base64
