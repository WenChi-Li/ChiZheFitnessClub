use MyGym

-------------------------------------------------------------------------------------------
create table GymUSerProfiles(
	userID int primary key identity(1000,1) not null, --�D��A�۰ʻ��W
	userName nvarchar(50) not null,  -- �|���m�W
	emailAddress nvarchar(50) not null unique,  -- �b���l��A�ݰߤ@
	userPassword nvarchar(MAX) not null,  -- �K�X�A�[�K�x�s
	registrationDate datetime DEFAULT GETDATE(),  -- ���U����A��e�ɶ�
	userPhone nvarchar(15),  --�q��
	userAddress nvarchar(100),  --�a�}
	gender nvarchar(10),  --�ʧO
	birthdate date,  --�ͤ�
	emergencyContactName nvarchar(50),  --����p���H�m�W
	emergencyContactPhone nvarchar(15)  --����p���H�q��
);

CREATE UNIQUE INDEX IDX_EmailAddress  --�s�W�ֳt�d�߱b��
ON GymUSerProfiles (emailAddress);

CREATE INDEX IDX_UserPhone  --�s�W�ֳt�d�߹q��
ON GymUSerProfiles (userPhone);

select*from GymUSerProfiles;

-- �R����ƪ�
 --drop table  GymUSerProfiles;

-- �R������ IDX_EmailAddress
-- DROP INDEX GymUSerProfiles.IDX_EmailAddress;

-- �R������ IDX_UserPhone
-- DROP INDEX GymUSerProfiles.IDX_UserPhone;
---------------------------------------------------------------------------------------------------

--�V�m�������D

create table TrainingRecordTitle(
	trainingTitleID int primary key identity(1000,1) not null, --�D��A�۰ʻ��W
	trainingTitle NVARCHAR(100) NOT NULL DEFAULT '���R�W�V�m', --�V�m���D
	trainingDate DATE DEFAULT GETDATE() not null, --�V�m����A�w�]���Ѥ��
	userID int not null,
	FOREIGN KEY (userID) REFERENCES GymUSerProfiles(userID) --�~��ϥΪ̹�V�m����(�@��h)
);

--�d�ߤ������
CREATE INDEX IDX_TrainingRecordTitle_Date ON TrainingRecordTitle (trainingDate);

select *from TrainingRecordTitle;

 -- �R����ƪ�
 --drop table TrainingRecordTitle;
 --�R���������
 --DROP INDEX TrainingRecordTitle.IDX_TrainingRecordTitle_Date;
 ---------------------------------------------------------------------------------------------------

 --�V�m����


 create table TrainingRecords(
	trainingID int primary key identity(1000,1) not null, --�D��A�۰ʻ��W
	exerciseType nvarchar(30) , --�B������
	trainingWeight DECIMAL(5, 1) , --�V�m���q
	repetitions int , --����
	trainingSets int , --�ռ�
	totalTrainingVolume AS (TrainingWeight * Repetitions * TrainingSets), --�V�m�e�n(�T�Ӭۭ�)
	
	trainingTitleID int not null,
	CONSTRAINT FK_TrainingRecords_TrainingRecordTitle --�ۭq�~��W��
	--�~��V�m���D��V�m����(�@��h)�A�~������A����R���ɦ۰ʧR���l������O��
	FOREIGN KEY (trainingTitleID) REFERENCES TrainingRecordTitle(trainingTitleID) ON DELETE CASCADE

 );

 --�d�߼��D����
 CREATE INDEX IDX_TrainingRecords_TitleID ON TrainingRecords (trainingTitleID);


 select *from TrainingRecords;
 

 --�s�W���e����
 INSERT INTO TrainingRecords 
    (exerciseType, trainingWeight, repetitions, trainingSets, trainingTitleID) 
 VALUES 
    ('�`��', 75.5, 10, 1, 1001);


 -- �R����ƪ�
 drop table TrainingRecords;

 --���repository��"�C��V�m�e�n�`�M"�g�k
SELECT 
    COUNT(DISTINCT CONVERT(DATE, T.trainingDate)) AS trainingDays, 
    SUM(R.totalTrainingVolume) AS totalVolume
FROM TrainingRecords R
INNER JOIN TrainingRecordTitle T ON R.trainingTitleID = T.trainingTitleID
WHERE T.userID = 1000  -- ��������� userID
  AND FORMAT(T.trainingDate, 'yyyy-MM') = '2025-01';


  --����repository�d�ߤ��M�󪺰V�m�аO
  SELECT t.trainingtitleid AS trainingTitleID, t.trainingdate AS trainingDate
FROM TrainingRecordTitle t
INNER JOIN GymUSerProfiles u ON t.userID = u.userID
WHERE u.emailAddress = 'ccc@gmail.com';
