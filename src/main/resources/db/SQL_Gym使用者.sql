use MyGym

-------------------------------------------------------------------------------------------
create table GymUSerProfiles(
	userID int primary key identity(1000,1) not null, --主鍵，自動遞增
	userName nvarchar(50) not null,  -- 會員姓名
	emailAddress nvarchar(50) not null unique,  -- 帳號郵件，需唯一
	userPassword nvarchar(MAX) not null,  -- 密碼，加密儲存
	registrationDate datetime DEFAULT GETDATE(),  -- 註冊日期，當前時間
	userPhone nvarchar(15),  --電話
	userAddress nvarchar(100),  --地址
	gender nvarchar(10),  --性別
	birthdate date,  --生日
	emergencyContactName nvarchar(50),  --緊急聯絡人姓名
	emergencyContactPhone nvarchar(15)  --緊急聯絡人電話
);

CREATE UNIQUE INDEX IDX_EmailAddress  --新增快速查詢帳號
ON GymUSerProfiles (emailAddress);

CREATE INDEX IDX_UserPhone  --新增快速查詢電話
ON GymUSerProfiles (userPhone);

select*from GymUSerProfiles;

-- 刪除資料表
 --drop table  GymUSerProfiles;

-- 刪除索引 IDX_EmailAddress
-- DROP INDEX GymUSerProfiles.IDX_EmailAddress;

-- 刪除索引 IDX_UserPhone
-- DROP INDEX GymUSerProfiles.IDX_UserPhone;
---------------------------------------------------------------------------------------------------

--訓練紀錄標題

create table TrainingRecordTitle(
	trainingTitleID int primary key identity(1000,1) not null, --主鍵，自動遞增
	trainingTitle NVARCHAR(100) NOT NULL DEFAULT '未命名訓練', --訓練標題
	trainingDate DATE DEFAULT GETDATE() not null, --訓練日期，預設今天日期
	userID int not null,
	FOREIGN KEY (userID) REFERENCES GymUSerProfiles(userID) --外鍵使用者對訓練紀錄(一對多)
);

--查詢日期索引
CREATE INDEX IDX_TrainingRecordTitle_Date ON TrainingRecordTitle (trainingDate);

select *from TrainingRecordTitle;

 -- 刪除資料表
 --drop table TrainingRecordTitle;
 --刪除日期索引
 --DROP INDEX TrainingRecordTitle.IDX_TrainingRecordTitle_Date;
 ---------------------------------------------------------------------------------------------------

 --訓練紀錄


 create table TrainingRecords(
	trainingID int primary key identity(1000,1) not null, --主鍵，自動遞增
	exerciseType nvarchar(30) , --運動類型
	trainingWeight DECIMAL(5, 1) , --訓練重量
	repetitions int , --次數
	trainingSets int , --組數
	totalTrainingVolume AS (TrainingWeight * Repetitions * TrainingSets), --訓練容積(三個相乘)
	
	trainingTitleID int not null,
	CONSTRAINT FK_TrainingRecords_TrainingRecordTitle --自訂外鍵名稱
	--外鍵訓練標題對訓練紀錄(一對多)，外鍵約束，父表刪除時自動刪除子表相關記錄
	FOREIGN KEY (trainingTitleID) REFERENCES TrainingRecordTitle(trainingTitleID) ON DELETE CASCADE

 );

 --查詢標題索引
 CREATE INDEX IDX_TrainingRecords_TitleID ON TrainingRecords (trainingTitleID);


 select *from TrainingRecords;
 

 --新增內容測試
 INSERT INTO TrainingRecords 
    (exerciseType, trainingWeight, repetitions, trainingSets, trainingTitleID) 
 VALUES 
    ('深蹲', 75.5, 10, 1, 1001);


 -- 刪除資料表
 drop table TrainingRecords;

 --後端repository的"每月訓練容積總和"寫法
SELECT 
    COUNT(DISTINCT CONVERT(DATE, T.trainingDate)) AS trainingDays, 
    SUM(R.totalTrainingVolume) AS totalVolume
FROM TrainingRecords R
INNER JOIN TrainingRecordTitle T ON R.trainingTitleID = T.trainingTitleID
WHERE T.userID = 1000  -- 替換為實際 userID
  AND FORMAT(T.trainingDate, 'yyyy-MM') = '2025-01';


  --測試repository查詢月曆套件的訓練標記
  SELECT t.trainingtitleid AS trainingTitleID, t.trainingdate AS trainingDate
FROM TrainingRecordTitle t
INNER JOIN GymUSerProfiles u ON t.userID = u.userID
WHERE u.emailAddress = 'ccc@gmail.com';
