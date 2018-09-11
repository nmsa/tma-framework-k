rbd create tma_k_db_mysql -s 1024
rbd feature disable tma_k_db_mysql fast-diff
rbd feature disable tma_k_db_mysql object-map
rbd feature disable tma_k_db_mysql deep-flatten
mkfs.ext4 /dev/rbd0
ceph auth get-key client.admin > temp.txt
key="$(sed -n 1p temp.txt)"
echo "${key}"| base64
