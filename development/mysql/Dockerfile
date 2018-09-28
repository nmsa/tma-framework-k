FROM mysql

# Create diretory of files to configure MySQL
WORKDIR /mysql

# Copy file that configure knowledge database
COPY TMA-K_create_database.sql /mysql/TMA-K_create_database.sql

# Copy file that inserts example data in knowledge database
COPY example_data_mysql.sql /mysql/TMA-K_insert_example_data.sql
