CREATE DATAbase BreakOutBall
go
USE [BreakOutBall]
GO
/****** Object:  Table [dbo].[Player]    Script Date: 10/31/2024 3:06:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Player](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nick_name] [nvarchar](50) NOT NULL,
	[score] [int] NULL,
	[life] [int] NULL,
	[ranker] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[nick_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[RankView]    Script Date: 10/31/2024 3:06:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[RankView] 
AS
SELECT id,nick_name,score,life,
	RANK() OVER (ORDER BY score DESC, life DESC) AS ranker
FROM Player
GO
/****** Object:  StoredProcedure [dbo].[AddPlayer]    Script Date: 10/31/2024 3:06:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[AddPlayer]
	@nickName nvarchar(50),
	@score int,
	@life int
AS
BEGIN
--Start transaction
	BEGIN TRANSACTION;
	--Add player
	INSERT INTO Player(nick_name,score,life) VALUES (@nickName,@score,@life);
	
	--Get rank player
	UPDATE pl
	SET pl.ranker = v.ranker
	FROM Player AS pl
	JOIN BreakOutBall.dbo.RankView AS v
	ON pl.id = v.id
	--Reset rank
	
	COMMIT TRANSACTION;
END;
GO
/****** Object:  StoredProcedure [dbo].[UpdatePlayer]    Script Date: 10/31/2024 3:06:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UpdatePlayer]
	@name NVARCHAR(50),
	@score int,
	@life int
AS
BEGIN
	DECLARE @id int
	--Get player
	SELECT TOP 1 
		@id = p.id
	FROM Player AS p
	WHERE p.nick_name = @name
	ORDER BY id DESC
	--Update player
	UPDATE Player
	SET score = @score, life = @life
	WHERE id = @id
	
	UPDATE p
	SET p.ranker = v.ranker
	FROM Player AS p
	JOIN BreakOutBall.dbo.RankView AS v
	ON p.id = v.id
END
GO
