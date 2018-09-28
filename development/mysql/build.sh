# Copy TMA-K_create_database.sql that creates tables and fields in database
cp ../../database/TMA-K_create_database.sql TMA-K_create_database.sql

# Create MySQL Docker image
docker build -t tma-knowledge/mysql:0.1 .

# Remove unnecessary file
rm TMA-K_create_database.sql
