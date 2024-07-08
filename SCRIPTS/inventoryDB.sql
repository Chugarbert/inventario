create database InventoryDB;
go
use InventoryDB;
-- Create Role tableGO
/****** Object:  Table [dbo].[role]    Script Date: 08/07/2024 01:38:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[role] ON 

INSERT [dbo].[role] ([id], [name]) VALUES (1, N'Administrador')
INSERT [dbo].[role] ([id], [name]) VALUES (2, N'Almacenista')
INSERT [dbo].[role] ([id], [name]) VALUES (3, N'guyRole')
SET IDENTITY_INSERT [dbo].[role] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK8sewwnpamngi6b1dwaa88askk]    Script Date: 08/07/2024 01:38:44 p. m. ******/
ALTER TABLE [dbo].[role] ADD  CONSTRAINT [UK8sewwnpamngi6b1dwaa88askk] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO

GO
/****** Object:  Table [dbo].[permissions]    Script Date: 08/07/2024 01:38:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permissions](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[permissions] ON 

INSERT [dbo].[permissions] ([id], [name]) VALUES (3, N'Agregar nuevos productos')
INSERT [dbo].[permissions] ([id], [name]) VALUES (4, N'Aumentar inventario')
INSERT [dbo].[permissions] ([id], [name]) VALUES (5, N'Dar de baja un producto')
INSERT [dbo].[permissions] ([id], [name]) VALUES (1, N'Iniciar session')
INSERT [dbo].[permissions] ([id], [name]) VALUES (6, N'Reactivar un producto')
INSERT [dbo].[permissions] ([id], [name]) VALUES (8, N'Sacar inventario del almacen')
INSERT [dbo].[permissions] ([id], [name]) VALUES (9, N'Ver modulo del historico')
INSERT [dbo].[permissions] ([id], [name]) VALUES (2, N'Ver modulo inventario')
INSERT [dbo].[permissions] ([id], [name]) VALUES (7, N'Ver modulo salida de productos')
SET IDENTITY_INSERT [dbo].[permissions] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKpnvtwliis6p05pn6i3ndjrqt2]    Script Date: 08/07/2024 01:38:44 p. m. ******/
ALTER TABLE [dbo].[permissions] ADD  CONSTRAINT [UKpnvtwliis6p05pn6i3ndjrqt2] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO


-- Create RolePermissions table
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role_permissions](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[permission_id] [bigint] NOT NULL,
	[role_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[role_permissions] ON 

INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (1, 1, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (2, 2, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (3, 3, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (4, 4, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (5, 5, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (6, 6, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (7, 9, 1)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (8, 1, 2)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (9, 2, 2)
INSERT [dbo].[role_permissions] ([id], [permission_id], [role_id]) VALUES (10, 8, 2)

SET IDENTITY_INSERT [dbo].[role_permissions] OFF
GO
ALTER TABLE [dbo].[role_permissions]  WITH CHECK ADD  CONSTRAINT [FKegdk29eiy7mdtefy5c7eirr6e] FOREIGN KEY([permission_id])
REFERENCES [dbo].[permissions] ([id])
GO
ALTER TABLE [dbo].[role_permissions] CHECK CONSTRAINT [FKegdk29eiy7mdtefy5c7eirr6e]
GO
ALTER TABLE [dbo].[role_permissions]  WITH CHECK ADD  CONSTRAINT [FKlodb7xh4a2xjv39gc3lsop95n] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[role_permissions] CHECK CONSTRAINT [FKlodb7xh4a2xjv39gc3lsop95n]
GO
GO
/****** Object:  Table [dbo].[users]    Script Date: 08/07/2024 01:38:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id_usuario] [int] IDENTITY(1,1) NOT NULL,
	[correo] [varchar](255) NULL,
	[estatus] [int] NULL,
	[nombre] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[id_rol] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id_usuario], [correo], [estatus], [nombre], [password], [id_rol]) VALUES (1, N'admin@example.com', 1, N'Admin', N'$2a$10$XUGKHWjv5vgioUNMiQJ92erY11pxQ/oTNKRiV3HL3OSCJ5fSchKg2', 1)
INSERT [dbo].[users] ([id_usuario], [correo], [estatus], [nombre], [password], [id_rol]) VALUES (2, N'user1@example.com', 1, N'User1', N'$2a$10$jZ48AjRKRyx4nAvJ81JGq.4zl2gaHPyzrwp1vyn.cdlNZzmb6IhaW', 2)
INSERT [dbo].[users] ([id_usuario], [correo], [estatus], [nombre], [password], [id_rol]) VALUES (3, N'user2@example.com', 1, N'User2', N'$2a$10$c8UlmKoJXST4F8PXwrPSge8mX0q7Yhi673yS/JgjrwNXH1J.Iw.W2', 3)
SET IDENTITY_INSERT [dbo].[users] OFF
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [FKs7cfh5vft9suwgrbi7pg1khdv] FOREIGN KEY([id_rol])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [FKs7cfh5vft9suwgrbi7pg1khdv]
GO


-- Create Products table
GO
/****** Object:  Table [dbo].[products]    Script Date: 08/07/2024 01:38:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[cantidad] [int] NOT NULL,
	[estatus] [int] NOT NULL,
	[nombre] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([id], [cantidad], [estatus], [nombre]) VALUES (1, 10, 0, N'PC')
INSERT [dbo].[products] ([id], [cantidad], [estatus], [nombre]) VALUES (2, 5, 1, N'SmartTV')
SET IDENTITY_INSERT [dbo].[products] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKcplu31m8uei2e5vn925pdxfnc]    Script Date: 08/07/2024 01:38:44 p. m. ******/
ALTER TABLE [dbo].[products] ADD  CONSTRAINT [UKcplu31m8uei2e5vn925pdxfnc] UNIQUE NONCLUSTERED 
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
GO
/****** Object:  Table [dbo].[inventory_movements]    Script Date: 08/07/2024 01:38:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[inventory_movements](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[date_time] [datetime2](6) NOT NULL,
	[quantity] [int] NOT NULL,
	[type] [varchar](255) NOT NULL,
	[product_id] [bigint] NOT NULL,
	[user_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[inventory_movements] ON 

INSERT [dbo].[inventory_movements] ([id], [date_time], [quantity], [type], [product_id], [user_id]) VALUES (1, CAST(N'2024-07-08T12:36:19.9693440' AS DateTime2), 10, N'ENTRADA', 1, 1)
INSERT [dbo].[inventory_movements] ([id], [date_time], [quantity], [type], [product_id], [user_id]) VALUES (2, CAST(N'2024-07-08T12:36:35.6991640' AS DateTime2), 5, N'ENTRADA', 2, 1)
SET IDENTITY_INSERT [dbo].[inventory_movements] OFF
GO
ALTER TABLE [dbo].[inventory_movements]  WITH CHECK ADD  CONSTRAINT [FK7lws1ve8g6b9itc054wj06uit] FOREIGN KEY([product_id])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[inventory_movements] CHECK CONSTRAINT [FK7lws1ve8g6b9itc054wj06uit]
GO
ALTER TABLE [dbo].[inventory_movements]  WITH CHECK ADD  CONSTRAINT [FKge9yf9e5bfabawl0j7olukqau] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id_usuario])
GO
ALTER TABLE [dbo].[inventory_movements] CHECK CONSTRAINT [FKge9yf9e5bfabawl0j7olukqau]
GO

-- Crear un nuevo inicio de sesión
CREATE LOGIN castores
WITH PASSWORD = 'M95QHZnCgbUmrF3';

-- Crear un nuevo usuario para la base de datos y mapearlo al inicio de sesión
USE InventoryDB;
CREATE USER castores FOR LOGIN castores;

-- Asignar roles de base de datos al usuario
ALTER ROLE db_owner ADD MEMBER castores;
