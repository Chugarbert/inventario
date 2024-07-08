create database InventoryDB;
go
use InventoryDB;
-- Create Role table
CREATE TABLE Role (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL UNIQUE
);
GO

-- Create Permissions table
CREATE TABLE Permissions (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- Create RolePermissions table
CREATE TABLE RolePermissions (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES Roles(id),
    FOREIGN KEY (permission_id) REFERENCES Permissions(id)
);
GO

-- Create usuarios table
CREATE TABLE users(
    idUsuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    correo NVARCHAR(25) NOT NULL UNIQUE,
    idRol INT NOT NULL,
    estatus INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES Roles(id)
);
GO

-- Create Products table
CREATE TABLE Products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    active BIT NOT NULL DEFAULT 1
);
GO

-- Create InventoryMovements table
CREATE TABLE InventoryMovements (
    id INT IDENTITY(1,1) PRIMARY KEY,
    type NVARCHAR(10) NOT NULL, -- "ENTRADA" or "SALIDA"
    quantity INT NOT NULL,
    dateTime DATETIME NOT NULL DEFAULT GETDATE(),
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (user_id) REFERENCES users(idUsuario)
);
GO

-- Insert Roles
INSERT INTO Roles (name) VALUES ('Administrador');
INSERT INTO Roles (name) VALUES ('Almacenista');
GO

-- Insert Permissions
INSERT INTO Permissions (name) VALUES ('Ver módulo inventario');
INSERT INTO Permissions (name) VALUES ('Agregar nuevos productos');
INSERT INTO Permissions (name) VALUES ('Aumentar inventario');
INSERT INTO Permissions (name) VALUES ('Dar de baja/reactivar un producto');
INSERT INTO Permissions (name) VALUES ('Ver módulo para Salida de productos');
INSERT INTO Permissions (name) VALUES ('Sacar inventario del almacén');
INSERT INTO Permissions (name) VALUES ('Ver módulo del histórico');
GO

-- Assign Permissions to Administrador
INSERT INTO RolePermissions (role_id, permission_id)
SELECT r.id, p.id
FROM Roles r, Permissions p
WHERE r.name = 'Administrador' AND p.name IN (
    'Ver módulo inventario',
    'Agregar nuevos productos',
    'Aumentar inventario',
    'Dar de baja/reactivar un producto',
    'Ver módulo del histórico'
);
GO

-- Assign Permissions to Almacenista
INSERT INTO RolePermissions (role_id, permission_id)
SELECT r.id, p.id
FROM Roles r, Permissions p
WHERE r.name = 'Almacenista' AND p.name IN (
    'Ver módulo inventario',
    'Ver módulo para Salida de productos',
    'Sacar inventario del almacén'
);
GO

-- Crear un nuevo inicio de sesión
CREATE LOGIN castores
WITH PASSWORD = 'M95QHZnCgbUmrF3';

-- Crear un nuevo usuario para la base de datos y mapearlo al inicio de sesión
USE InventoryDB;
CREATE USER castores FOR LOGIN castores;

-- Asignar roles de base de datos al usuario
ALTER ROLE db_owner ADD MEMBER castores;
