#Create Ceph image
rbd create tma_k_db_mysql -s 1024

#Disable some image features that are not supported by Linux Kernel
rbd feature disable tma_k_db_mysql fast-diff
rbd feature disable tma_k_db_mysql object-map
rbd feature disable tma_k_db_mysql deep-flatten

# Assing a file system type to image created
mkfs.ext4 /dev/rbd0

#Generate authentication key to connecting Kubernetes and Ceph
ceph auth get-key client.admin > temp.txt
key="$(sed -n 1p temp.txt)"
echo "${key}"| base64
