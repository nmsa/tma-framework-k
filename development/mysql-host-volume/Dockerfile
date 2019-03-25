FROM mysql

ENV MYSQL_DATABASE knowledge

# Create diretory of files to configure MySQL
WORKDIR /mysql

# Copy file that configure knowledge database
COPY TMA-K_create_database.sql /docker-entrypoint-initdb.d/

# Copy file that inserts example data in knowledge database
COPY TMA-K_insert_example_data.sql /mysql/TMA-K_insert_example_data.sql
