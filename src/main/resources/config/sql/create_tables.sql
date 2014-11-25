------------------------------------------------------------------------------
--
--	左边网数据库创建脚本
--	
--  1)创建表(Table)
--  2)创建主键约束(PK)
-- 	3)创建唯一性约束(Unique)
--	4)创建序列(Sequence)
--  5)创建索引(Index)
--
------------------------------------------------------------------------------
------------------------------------------------------------------------------
--
--命名规范：
--表名          	表名
--主键名        	pk_表名
--外键名        	fk_当前表名_参照表名_列名
--非唯一索引名  	idx_表名_列名
--唯一索引名    	unique_表名_列名
--
------------------------------------------------------------------------------
--创建表空间
--CREATE TABLESPACE zuobian_data datafile '/opt/oracle_11/app/oradata/zuobian/zuobian_data01.bdf' size 5120M,  '/opt/oracle_11/app/oradata/zuobian/zuobian_data02.bdf' size 5120M EXTENT MANAGEMENT LOCAL;
--增加Oracle表空间
--先查询数据文件名称、大小和路径的信息，语句如下：
--select tablespace_name,file_id,bytes,file_name from dba_data_files;
--为表空间增加数据文件：  
-- alter tablespace zuobian_data add  
 --datafile '/opt/oracle_11/app/oradata/zuobian/zuobian_data01.bdf' size 800M  
 --autoextend on next 50M  
 --maxsize 1000M;

------------------------------------------------------------------创建表--BEGIN-----------------------------------------------------------------

----------------------
--
-- ####################################################公司员工模块###################################################
--
----------------------
--公司信息表
CREATE TABLE TRAVEL_COMPANY 
(
   C_ID                 NUMBER(20)         NOT NULL, --自动编号,主键
   GMT_CREATE 			TIMESTAMP 		   NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  		   NOT NULL, --记录最后修改时间
   
   C_TYPE               NUMBER(10),					   --公司类型
   C_NAME               VARCHAR2(500),--公司名称
   C_PROVINCE           VARCHAR2(200),--省
   C_CITY               VARCHAR2(200),--市
   C_COUNTY             VARCHAR2(200),--区/县
   C_CUSTOMNAME         VARCHAR2(200),--联系人
   C_LOGO               VARCHAR2(200),--公司Logo
   C_QQ                 VARCHAR2(200),--公司qq
   C_EMAIL              VARCHAR2(200),--电子邮件
   C_TEL                VARCHAR2(200),--电话
   C_FAX                VARCHAR2(200),--传真
   C_MOBILE             VARCHAR2(200),--手机
   C_ADDRESS            VARCHAR2(500),--公司地址
   C_ABOUTUS            NCLOB,--公司简介
   C_CONTACT            NCLOB,--公司联系内容，富文本
   C_DEFAULTCITY        VARCHAR2(500),--组团社默认登录城市（格式：浙江|杭州）
   C_CITYTOP            VARCHAR2(500),--默认站点（当前IP站点如果不在站点列表里，默认一个站点）
   C_CITYLIST           NCLOB,--组团社可访问城站（浙江|杭州,上海|上海）
   C_BANK               NCLOB,--公司银行账户
   C_CORPORATION        VARCHAR2(200),--公司法人
   C_RECOMMEND			VARCHAR2(100),--推荐公司，或者推荐人
   C_LOGINTIME          DATE,--登录日期
   C_STATE              NUMBER(10)           default 0  NOT NULL, --状态（0=未审核，1=正常，2=停止）
   C_SPELL				VARCHAR2(500)
);
ALTER TABLE TRAVEL_COMPANY ADD CONSTRAINT PK_TRAVEL_COMPANY PRIMARY KEY (C_ID);
CREATE SEQUENCE TRAVEL_COMPANY_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_COMPANY_EMAIL on TRAVEL_COMPANY(C_EMAIL);
--ALTER TABLE TRAVEL_COMPANY ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);


--公司员工表
CREATE TABLE TRAVEL_MEMBER 
(
   M_ID                 NUMBER(20)         NOT NULL, --自动编号，主键
   GMT_CREATE 			TIMESTAMP 		   NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  		   NOT NULL, --记录最后修改时间
   
   C_ID                 NUMBER(20),--公司ID
   M_PIC                VARCHAR2(2000),--头像
   M_USERNAME           VARCHAR2(200),--用户名
   M_PASSWORD           VARCHAR2(500),--密码
   M_NAME               VARCHAR2(200),--姓名
   M_SEX                NUMBER(10),--性别 0：男 1：女
   M_MOBILE             VARCHAR2(200),--手机号码
   M_TEL                VARCHAR2(200),--电话
   M_EMAIL              VARCHAR2(500),--电子邮件
   M_FAX                VARCHAR2(200),--传真
   M_QQ                 VARCHAR2(200),--QQ号
   M_TYPE               NUMBER(10),--类型
   M_ROLE               NCLOB,--权限
   M_STATE              NUMBER(10),--状态0=正常，1=停止
   M_LASTLOGINTIME		TIMESTAMP			
);
ALTER TABLE TRAVEL_MEMBER ADD CONSTRAINT PK_TRAVEL_MEMBER PRIMARY KEY (M_ID);
CREATE SEQUENCE TRAVEL_MEMBER_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_MEMBER_MOBILE on TRAVEL_MEMBER(M_MOBILE);
--ALTER TABLE TRAVEL_MEMBER ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);


--客服
CREATE TABLE TRAVEL_SERVICE
(
   S_ID              NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   C_ID              NUMBER(20),      	--公司ID
   S_NAME			 VARCHAR2(200),	  	--客服名称
   S_QQ		 		 VARCHAR2(50),  	--客服QQ
   S_SORT			 NUMBER(9),  		--排序(大号排前)
   S_MOBILE			 VARCHAR2(200),		--客服手机
   S_ISRECEIVE		 NUMBER(9)			--是否接受信息
);
ALTER TABLE TRAVEL_SERVICE ADD CONSTRAINT PK_TRAVEL_SERVICE PRIMARY KEY(S_ID);
CREATE SEQUENCE TRAVEL_SERVICE_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--黑名单表
CREATE TABLE TRAVEL_BLACKLIST
(
	B_ID			NUMBER(20)		NOT NULL,	--图片编号
	GMT_CREATE		TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED	TIMESTAMP		NOT NULL,	--修改时间
	
	BE_C_ID			NUMBER(20),					--被拉黑公司ID
	C_ID			NUMBER(20),					--操作公司ID
	M_ID			NUMBER(20),	    			--操作用户ID
	B_REMARK		VARCHAR2(300)				--拉黑备注
);
ALTER TABLE TRAVEL_BLACKLIST ADD CONSTRAINT PK_TRAVEL_BLACKLIST PRIMARY KEY(B_ID);
CREATE SEQUENCE TRAVEL_BLACKLIST_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;



----------------------
--
-- ####################################################产品模块###################################################
--
----------------------
--旅游产品（批发商发布），产品线路表
CREATE TABLE TRAVEL_LINE 
(
   L_ID                 NUMBER(20)         		NOT NULL,
   GMT_CREATE 			TIMESTAMP 			 	NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  			 	NOT NULL, --记录最后修改时间
   
   Z_ID                 NUMBER(20), --专线ID
   M_ID                 NUMBER(20), --用户ID（发布人）
   C_ID                 NUMBER(20),	--公司ID（发布人公司）
   L_TITLE              VARCHAR2(500),	--标题
   L_TYPE               NUMBER(10),	--类型
   L_GROUPNUMBER        VARCHAR2(200),	--团号
   L_GOGROUPTIME        DATE,	--出团日期
   L_ENDTIME            DATE,	--截止日期
   L_PROVINCE           VARCHAR2(200),	--省
   L_CITY               VARCHAR2(200),	--市
   L_AREA               VARCHAR2(200),	--区/县
   L_ISJS               NUMBER(10),	--是否带接送（0=不带，1=带）
   L_JSRUD              NCLOB,	--接送区域
   L_ARRIVALPROVINCE    VARCHAR2(200),	--抵达省
   L_ARRIVALCITY        VARCHAR2(200),	--抵达市
   L_ARRIVALAREA        VARCHAR2(200),	--抵达区/县
   L_RENCOUNT           NUMBER(10),	--总人数
   L_CRCOUNT            NUMBER(10),	--已订成人数
   L_XHCOUNT            NUMBER(10),	--已订儿童数
   L_YCOUNT             NUMBER(10),	--已订婴儿数
   L_CRPRICE            FLOAT,	--成人门市价
   L_JCRPRICE           FLOAT,	--成人结算价
   L_XHPRICE            FLOAT,	--儿童门市价
   L_JXHPRICE           FLOAT,	--儿童结算价
   L_YPRICE             FLOAT,	--婴儿结算价
   L_JYPRICE            FLOAT,	--婴儿门市价
   L_FANGPRICE          FLOAT,	--单房差
   
   L_ADULTINTEGRAL		NUMBER(9),--成人积分
   L_CHILDRENINTEGRAL	NUMBER(9),--儿童积分
   
   L_ISINTEGRAL			NUMBER(9),--是否积分
   L_ISVOUCHERS			NUMBER(9),--是否可用抵用券
   
   L_DAY                NUMBER(10),	--旅游天数
   L_TRAFFICTYPE        NUMBER(10),	--出发交通类型（1=飞机,2=汽车,3=火车,4=游船,5=待定）
   L_GOTRAFFIC          VARCHAR2(500),	--往，交通介绍
   L_TRAFFICBACKTYPE    NUMBER(10),	--返回交通类型（1=飞机,2=汽车,3=火车,4=游船,5=待定）
   L_BACKTRAFFICE       VARCHAR2(500),	--返，交通介绍
   L_JHD                VARCHAR2(200),	--集合地
   L_JHDTIME            VARCHAR2(200),	--集合时间
   L_ICO                VARCHAR2(200),	--送团标志
   L_DIPEI              VARCHAR2(200),	--地接
   L_GROUPTEL           VARCHAR2(200),	--送团电话
   L_MODE               NCLOB,	--线路备注
   L_NMODE              NCLOB,	--线路内部备注
   L_YESITEM            NCLOB,	--包含项目
   L_NOITEM             NCLOB,	--不包含项目
   L_CHILDREN           NCLOB,	--儿童安排
   L_SHOP               NCLOB,	--购物安排
   L_EXPENSEITEM        NCLOB,	--自费项目
   L_PRESEITEM          NCLOB,	--赠送项目
   L_ATTENTION          NCLOB,	--注意事项
   L_OTHER              NCLOB,	--其他事项
   L_REMINDER           NCLOB,	--温馨提示
   L_TOURCONTENT		NCLOB,	--法律信息
   L_TEMPLATESATE       NUMBER(10),	--0=线路,1=模板
   L_EDITUSERID         number(20),	--最后修改人ID
   L_STATE              NUMBER(10)           default 0,	--状态（0=正常,1=停止,2=客满,3=过期）
   L_DELSTATE           NUMBER(10)           default 0,	--0=正常,1=删除
   L_DISPLAY            NUMBER(10)           default 0, --显示状态（0=显,1=隐）
   L_PRODUCT            VARCHAR2(50),	--所属产品
   L_PHOTOCOVER			VARCHAR2(500), --封面地址
   L_VIEWS          	NUMBER(20)  --浏览量
);
ALTER TABLE TRAVEL_LINE ADD CONSTRAINT PK_TRAVEL_LINE PRIMARY KEY (L_ID);
CREATE SEQUENCE TRAVEL_LINE_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_LINE_EMAIL on TRAVEL_LINE(C_EMAIL);
--ALTER TABLE TRAVEL_LINE ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);

--线路行程表
CREATE TABLE TRAVEL_ROUTE 
(
   R_ID                 NUMBER             		NOT NULL,
   GMT_CREATE 			TIMESTAMP 			 	NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  			 	NOT NULL, --记录最后修改时间
   --R_STATE            NUMBER(10),--状态(0=正常，1=停止)
   
   L_ID                 NUMBER,	--线路ID
   R_CONTENT            NCLOB,	--内容
   R_ZHU                VARCHAR2(1000),	--住宿
   R_CAN                VARCHAR2(1000),	--餐（0=早，1=中，2=晚）
   R_PICPATH            VARCHAR2(500),	--图片地址（每个路径用英文逗号隔开）
   R_CAR                VARCHAR2(200),	--交通计划
   R_RESOURCE           varchar2(1000)	--资源ID（每个 ID用英文逗号隔开）
);
ALTER TABLE TRAVEL_ROUTE ADD CONSTRAINT PK_TRAVEL_ROUTE PRIMARY KEY (R_ID);
CREATE SEQUENCE TRAVEL_ROUTE_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_ROUTE_EMAIL on TRAVEL_ROUTE(C_EMAIL);
--ALTER TABLE TRAVEL_ROUTE ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);

--交通模板表
CREATE TABLE TRAVEL_TRAFFIC
(
	T_ID				   	NUMBER(10)			NOT NULL, --自动编号
	GMT_CREATE 				TIMESTAMP 		   	NOT NULL, --记录创建时间
    GMT_MODIFIED 			TIMESTAMP  		   	NOT NULL, --记录最后修改时间
   
	T_TYPE					NUMBER(9)			NOT NULL,	 	--0=飞机,1=汽车,2=火车,3=其它
	T_CAT				   	NUMBER(9)			NOT NULL,	 	--往=0，返=1
	T_TRAFFIC				VARCHAR2(300)		NOT NULL,	 	--交通备注
	C_ID				   	NUMBER(10)	 		NOT NULL		--公司ID(当前登录公司)
);
ALTER TABLE TRAVEL_TRAFFIC ADD CONSTRAINT PK_TRAVEL_TRAFFIC PRIMARY KEY(T_ID);
CREATE SEQUENCE  TRAVEL_TRAFFIC_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;


----------------------
--
-- ####################################################订单模块###################################################
--
----------------------
--订单表（批发商（地接社） --> 经销商（组团社））
CREATE TABLE TRAVEL_ORDER 
(
   OR_ID                NUMBER(20)         NOT NULL,
   GMT_CREATE 			TIMESTAMP 		   NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  		   NOT NULL, --记录最后修改时间
   
   L_ID                 NUMBER(20),--产品线路ID
   CUSTOM_ID            NUMBER(20),--预订人ID
   CUSTOMCOMPANY_ID     NUMBER(20),--预订人公司ID
   C_ID                 NUMBER(20),--接收订单公司ID
   M_ID                 NUMBER(20),--预留人ID
   OR_ORDERID           VARCHAR2(100),--订单流水号
   OR_NAME              VARCHAR2(30),--预订人姓名
   OR_MOBILE            VARCHAR2(30),--预订人手机
   OR_TEL               VARCHAR2(200),--预订人电话
   OR_FAX               VARCHAR2(100),--预订人传真
   OR_ADULTCOUNT        NUMBER(10)           default 0,--成人数
   OR_CHILDCOUNT        NUMBER(10)           default 0,--儿童数
   OR_BABYCOUNT         NUMBER(10)           default 0,--婴儿数
   OR_PIRCECOUNT        FLOAT                default 0,--订单总金额
   OR_YOUHUIPRICE       FLOAT                default 0,--优惠价
   OR_DANGFANGPRICE     FLOAT                default 0,--单房差总价
   OR_MODE              NCLOB,--订单备注
   OR_GOGROUPTIME       DATE,--出团日期
   OR_CLEARMODE         NCLOB,--取消原因
   OR_NMODE             NCLOB,--内部备注
   OR_JS                NUMBER(20),--接收ID
   OR_JSPRICE           FLOAT,--接收价格
   OR_POSTSITE          VARCHAR2(200),--来源
   OR_FIRSTCRPRICE      FLOAT,--初始成人门市价
   OR_FIRSTJCRPRICE     FLOAT,--初始成人结算价
   OR_FIRSTXHPRICE      FLOAT,--初始儿童门市价
   OR_FIRSTJXHPRICE     FLOAT,--初始儿童结算价
   OR_FIRSTYPRICE       FLOAT,--初始婴儿门市价
   OR_FIRSTJYPRICE      FLOAT,--初始婴儿结算价
   OR_FIRSTFANGPRICE    FLOAT,--初始单房差
   OR_ADULTINTEGRAL		NUMBER(9),--成人积分
   OR_CHILDRENINTEGRAL	NUMBER(9),--儿童积分
   OR_STATE             NUMBER(10)           default 0--状态
);
ALTER TABLE TRAVEL_ORDER ADD CONSTRAINT PK_TRAVEL_ORDER PRIMARY KEY (OR_ID);
CREATE SEQUENCE TRAVEL_ORDER_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_ORDER_EMAIL on TRAVEL_LINE(C_EMAIL);
--ALTER TABLE TRAVEL_COMPANY ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);

--订单绑定的旅游团体，订单游客名单表
CREATE TABLE TRAVEL_ORDERGUEST 
(
   G_ID                 NUMBER(20)           NOT NULL,
   GMT_CREATE 			TIMESTAMP 			 NOT NULL, --记录创建时间
   GMT_MODIFIED 		TIMESTAMP  			 NOT NULL, --记录最后修改时间
   --G_STATE             NUMBER(10)           default 0,--状态	
   						
   OR_ID                NUMBER(20),--订单ID
   L_ID                 NUMBER(20),--线路ID
   G_NAME               VARCHAR2(30),--姓名
   G_SEX                NUMBER(10),--性别
   G_TYPE               NUMBER(10),--类型
   G_CARD               VARCHAR2(20),--身份证号
   G_MOBILE             VARCHAR2(15),--手机号码
   G_DANGFANG           NUMBER(10),--是否单房差
   G_MODE               VARCHAR2(100),--备注
   G_COSTPRICE          FLOAT,--订单小计
   G_DANGFANGPRICE      FLOAT,--单房差
   G_YOUHUI             FLOAT                default 0,--优惠价
   G_JSPRICE            FLOAT--接送价
);
ALTER TABLE TRAVEL_ORDERGUEST ADD CONSTRAINT PK_TRAVEL_ORDERGUEST PRIMARY KEY (G_ID);
CREATE SEQUENCE TRAVEL_ORDERGUEST_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_ORDERGUEST_EMAIL on TRAVEL_ORDERGUEST(C_EMAIL);
--ALTER TABLE TRAVEL_ORDERGUEST ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);


----------------------
--
-- ####################################################站点模块###################################################
--
----------------------
--站点管理表
CREATE TABLE TRAVEL_SITE 
(
   S_ID                 NUMBER(20)           NOT NULL,
   GMT_CREATE           TIMESTAMP            NOT NULL, --记录创建时间
   GMT_MODIFIED         TIMESTAMP            NOT NULL, --记录最后修改时间
   
   S_NAME               VARCHAR2(300),--站点名称
   S_PROVINCE           VARCHAR2(300),--站点所属省
   S_CITY               VARCHAR2(300),--站点所属市
   S_TOID               NUMBER(20),--站点上级ID
   S_SORT				NUMBER(9),--排序
   S_STATE              NUMBER(10)--状态(0=正常，1=停止)
);
ALTER TABLE TRAVEL_SITE ADD CONSTRAINT PK_TRAVEL_SITE PRIMARY KEY (S_ID);
CREATE SEQUENCE TRAVEL_SITE_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_SITE_EMAIL on TRAVEL_SITE(C_EMAIL);
--ALTER TABLE TRAVEL_SITE ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);

--站点下专线表，专线分类
CREATE TABLE TRAVEL_COLUMN 
(
   Z_ID                 NUMBER(20)           NOT NULL,
   GMT_CREATE           TIMESTAMP            NOT NULL, --记录创建时间
   GMT_MODIFIED         TIMESTAMP            NOT NULL, --记录最后修改时间
   
   S_ID 				NUMBER(10),--站点ID
   S_TOID               NUMBER(10),--出港点ID
   Z_NAME               VARCHAR2(500),--专线名称
   Z_CAT                NUMBER(10),--专线分类(0=短线，1=长线，2=国际线)
   Z_DESC               NUMBER(20),--专线排序(大号排前)
   Z_HOT                NUMBER(10),--状态(1=热门，2=推荐)
   Z_PIC                VARCHAR2(500),--专线图片
   Z_STATE              NUMBER(10)--状态(0=正常，1=停止)
);
ALTER TABLE TRAVEL_COLUMN ADD CONSTRAINT PK_TRAVEL_COLUMN PRIMARY KEY (Z_ID);
CREATE SEQUENCE TRAVEL_COLUMN_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_COLUMN_EMAIL on TRAVEL_COLUMN(C_EMAIL);
--ALTER TABLE TRAVEL_COLUMN ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);

--专线与公司关联表，公司所属专线
CREATE TABLE TRAVEL_LINECOLUMN 
(
   LC_ID                NUMBER(20)           NOT NULL,
   GMT_CREATE           TIMESTAMP            NOT NULL, --记录创建时间
   GMT_MODIFIED         TIMESTAMP            NOT NULL, --记录最后修改时间
   --LC_STATE              NUMBER(10),--状态(0=正常，1=停止)
   
   Z_ID					NUMBER(20),--专线ID
   C_ID                 NUMBER(20)--公司ID
);
ALTER TABLE TRAVEL_LINECOLUMN ADD CONSTRAINT PK_TRAVEL_LINECOLUMN PRIMARY KEY (LC_ID);
CREATE SEQUENCE TRAVEL_LINECOLUMN_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;
--CREATE index TRAVEL_LINECOLUMN_EMAIL on TRAVEL_LINECOLUMN(C_EMAIL);
--ALTER TABLE TRAVEL_LINECOLUMN ADD CONSTRAINT name_type UNIQUE(C_NAME,C_TYPE);


----------------------
--
-- ####################################################积分模块###################################################
--
----------------------
--积分产品表
CREATE TABLE TRAVEL_GIFT
(
   G_ID              	NUMBER(20)     		NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         	NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         	NOT NULL, 		--记录最后修改时间
   
   G_TITLE            	VARCHAR2(200),      --积分产品标题
   GC_ID              	NUMBER(10),      	--积分产品分类
   G_REDEMPTION       	FLOAT,      		--兑换积分
   G_PRICE            	FLOAT,   			--市场价格
   G_CONTENT          	VARCHAR2(500),		--内容/备注
   G_PIC              	VARCHAR2(2000),		--产品图片
   G_STATE            	NUMBER(9)			--状态(0=正常,1=停止)
);
ALTER TABLE TRAVEL_GIFT ADD CONSTRAINT PK_TRAVEL_GIFT PRIMARY KEY(G_ID);
CREATE SEQUENCE TRAVEL_GIFT_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;

--积分产品分类表
CREATE TABLE TRAVEL_GIFTCLASS
(
   GC_ID             	NUMBER(10)        NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   GC_NAME           	VARCHAR2(200),      --分类名称
   GC_PARENTID       	NUMBER(10)      	 --父类ID
);
ALTER TABLE TRAVEL_GIFTCLASS ADD CONSTRAINT PK_TRAVEL_GIFTCLASS PRIMARY KEY(GC_ID);
CREATE SEQUENCE TRAVEL_GIFTCLASS_SEQ INCREMENT BY 1  START WITH 1 NOMAXVALUE;

--积分历史交易表
CREATE TABLE TRAVEL_INTEGRALDEAL 
(
   ID_ID             	NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   C_ID              	NUMBER(20),      	--公司ID
   M_ID              	NUMBER(20),      	--用户ID
   ID_TYPE           	VARCHAR2(200),  	--积分类型(0=可用积分,1=冻结积分)
   ID_INTEGRAL       	NUMBER(10),     	--消耗积分
   GO_ID             	NUMBER(10),     	--积分订单ID
   G_ID              	NUMBER(20),       	--积分产品ID
   L_ID				 	NUMBER(20),		--消费产品ID
   ID_REMARK			VARCHAR(200)		--备注
);
ALTER TABLE TRAVEL_INTEGRALDEAL ADD CONSTRAINT PK_TRAVEL_INTEGRALDEAL PRIMARY KEY(ID_ID);
CREATE SEQUENCE TRAVEL_INTEGRALDEAL_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--积分订单表
CREATE TABLE TRAVEL_GIFTORDER 
(
   GO_ID             	NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   C_ID              	NUMBER(20),       --公司ID
   M_ID              	NUMBER(20),       --用户ID
   G_ID            	 	NUMBER(20),	   --积分产品ID
   GO_NAME        	 	VARCHAR2(100),    --订单名称
   GO_PROVINCE       	VARCHAR2(200),    --省
   GO_CITY           	VARCHAR2(200),    --市
   GO_COUNTY		 	VARCHAR2(200),    --区/县
   GO_ADDRESS		 	VARCHAR2(200),    --详细地址
   GO_MOBILE		 	VARCHAR2(200),	   --手机
   GO_TEL			 	VARCHAR2(200),	   --电话
   GO_EMAIL			 	VARCHAR2(200),	   --邮箱
   GO_STATE			 	NUMBER(10),	   --状态(0=正常,1=停止)
   GO_COUNT			 	NUMBER(10),	   --兑换数量
   GO_INTEGRALCOUNT	 	FLOAT   		   --所需积分
);
ALTER TABLE TRAVEL_GIFTORDER ADD CONSTRAINT PK_TRAVEL_GIFTORDER PRIMARY KEY(GO_ID);
CREATE SEQUENCE TRAVEL_GIFTORDER_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--积分表
CREATE TABLE TRAVEL_INTEGRAL
(
   I_ID              	NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   C_ID              	NUMBER(20),      	--公司ID
   M_ID              	NUMBER(20),      	--用户ID
   I_SOURCE          	NUMBER(10),	  	--积分来源(0=消费产品,1=返还积分,2=转入,3=充值)
   L_ID				 	NUMBER(20),		--消费产品ID
   I_BALANCE	 	 	NUMBER(20),   			--可用积分
   I_FROZEN	 	 		NUMBER(20),   			--冻结积分
   I_ALTOGETHER	 	 	NUMBER(20),   			--总积分
   I_ADDINTEGRAL	 	NUMBER(20),  			--本次新增积分
   I_REMARK			 	VARCHAR(200)		--备注
);
ALTER TABLE TRAVEL_INTEGRAL ADD CONSTRAINT PK_TRAVEL_INTEGRAL PRIMARY KEY(I_ID);
CREATE SEQUENCE TRAVEL_INTEGRAL_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;


----------------------
--
-- ####################################################CMS模块###################################################
--
----------------------
--公司新闻
CREATE TABLE TRAVEL_NEWS
(
   N_ID              	NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        	TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      	TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   C_ID              	NUMBER(20),      --公司ID
   Z_ID              	NUMBER(20),      --专线ID
   N_TYPE          	 	NUMBER(9),	      --新闻类型
   N_TITLE			 	VARCHAR2(200),	  --新闻标题
   N_PIC			 	VARCHAR2(2000),  --新闻图片或广告图片
   N_CONTENT		 	NCLOB,           --新闻内容
   N_STATE			 	NUMBER(20),      --状态(0=正常,1=停止)
   N_HOTCOUNT	     	NUMBER(20)       --浏览量
);
ALTER TABLE TRAVEL_NEWS ADD CONSTRAINT PK_TRAVEL_NEWS PRIMARY KEY(N_ID);
CREATE SEQUENCE TRAVEL_NEWS_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--文章管理表
CREATE TABLE TRAVEL_ARTICLES
(
    A_ID				NUMBER(20)		NOT NULL,	--文章编号
	GMT_CREATE			TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED		TIMESTAMP		NOT NULL,	--修改时间
	
	SOURCE  			NUMBER(9),						--文章类别来源
	TITLE  				VARCHAR2(300),					--文章标题
	CONTENT   			NCLOB,							--文章内容
	SORT  				NUMBER(9),						--文章排序
	STATE 				NUMBER(9)						--文章状态
);
ALTER TABLE TRAVEL_ARTICLES ADD CONSTRAINT PK_TRAVEL_ARTICLES PRIMARY KEY(A_ID);
CREATE SEQUENCE TRAVEL_ARTICLES_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--广告管理表
CREATE TABLE TRAVEL_ADVERTISEMENT
(
	AD_ID				NUMBER(20)		NOT NULL,	--广告编号
	GMT_CREATE			TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED		TIMESTAMP		NOT NULL,	--修改时间
	
	LOCATION  			NUMBER(9),						--广告位置
	SITE  				VARCHAR2(100),					--广告站点
	SITE_ID  			NUMBER(20),						--广告站点ID
	TITLE  				VARCHAR2(300),					--广告标题
	CONTENT   			NCLOB,							--广告内容
	PIC  				VARCHAR2(300),					--广告图片
	LINK 				VARCHAR2(300),					--广告链接
	SORT  				NUMBER(9),						--广告排序
	STATE 				NUMBER(9)						--广告状态
);
ALTER TABLE TRAVEL_ADVERTISEMENT ADD CONSTRAINT PK_TRAVEL_ADVERTISEMENT PRIMARY KEY(AD_ID);
CREATE SEQUENCE TRAVEL_ADVERTISEMENT_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;



----------------------
--
-- ####################################################财务模块###################################################
--
----------------------
--财务表
CREATE TABLE TRAVEL_FINANCE
(
   F_ID              NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   OR_ID         	 NUMBER(20),  		--订单ID
   F_TYPE         	 NUMBER(10),      	--财务类型
   F_SERIALNUMBER	 VARCHAR2(50), 		--流水号
   ACCOUNT_C_ID		 NUMBER(20),  		--地接社公司ID
   TOUR_C_ID 		 NUMBER(20),  		--组团社公司ID
   F_RECEIVABLE		 FLOAT,				--应收款
   F_RECEIPT		 FLOAT,				--已收款
   APP_ID			 NUMBER(20)     	--最后修改操作人ID
);
ALTER TABLE TRAVEL_FINANCE ADD CONSTRAINT PK_TRAVEL_FINANCE PRIMARY KEY(F_ID);
CREATE SEQUENCE TRAVEL_FINANCE_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--财务详情表
CREATE TABLE TRAVEL_FINANCE_DETAIL
(
   FD_ID             NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   F_ID              NUMBER(20),      	--财务ID
   M_ID           	 NUMBER(20),      	--操作人ID
   FD_SAVETIME  	 TIMESTAMP, 		--收款时间
   FD_TYPE 			 NUMBER(9),			--(0=收款，1=退款)
   FD_PRICE 		 FLOAT,				--价格
   FD_PAY 			 NUMBER(9),         --支付方式(0=支票,1=现金,2=电汇,3=其它)
   FD_PAYBANK 		 VARCHAR2(50),		--银行
   FD_REMARK 		 VARCHAR2(200)		--备注
);
ALTER TABLE TRAVEL_FINANCE_DETAIL ADD CONSTRAINT PK_TRAVEL_FINANCE_DETAIL PRIMARY KEY(FD_ID);
CREATE SEQUENCE TRAVEL_FINANCE_DETAIL_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;



----------------------
--
-- ####################################################消息模块###################################################
--
----------------------
--消息表
CREATE TABLE TRAVEL_MESSAGE
(
   ME_ID             NUMBER(20)        NOT NULL,      	--自动编号
   GMT_CREATE        TIMESTAMP         NOT NULL, 		--记录创建时间
   GMT_MODIFIED      TIMESTAMP         NOT NULL, 		--记录最后修改时间
   
   FROM_C_ID         NUMBER(20),  		--创建者公司ID
   FROM_M_ID         NUMBER(20),      	--创建者用户ID
   FROM_C_TYPE       NUMBER(10),	  	--创建者公司类型
   TO_C_ID           NUMBER(20),      	--接收者公司ID
   TO_M_ID           NUMBER(20),      	--接收者用户ID
   TO_C_TYPE 	     NUMBER(10),	  	--接收者公司类型
   TITLE			 VARCHAR2(200),	  	--消息标题
   CONTENT			 NCLOB, 	        --消息内容
   MESSAGE_STATE	 NUMBER(9), 	  	--消息状态(0=已读，1=未读)
   MESSAGE_TYPE	     NUMBER(9)  	  	--消息类型(0=订单未读，1=订单修改，2=订单取消，3=订单恢复)
);
ALTER TABLE TRAVEL_MESSAGE ADD CONSTRAINT PK_TRAVEL_MESSAGE PRIMARY KEY(ME_ID);
CREATE SEQUENCE TRAVEL_MESSAGE_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;



----------------------
--
-- ####################################################图片资源模块###################################################
--
----------------------
--图片表
CREATE TABLE TRAVEL_PHOTO
(
	P_ID			NUMBER(20)		NOT NULL,	--图片编号
	GMT_CREATE		TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED	TIMESTAMP		NOT NULL,	--修改时间
	
	C_ID			NUMBER(20),					--公司ID
	P_TYPE			NUMBER(9),					--图片类型
	P_TITLE			VARCHAR2(50),				--图片标题
	P_PATH			VARCHAR2(100),				--图片地址
	P_REMARK		VARCHAR2(300)				--图片备注
);
ALTER TABLE TRAVEL_PHOTO ADD CONSTRAINT PK_TRAVEL_PHOTO PRIMARY KEY(P_ID);
CREATE SEQUENCE TRAVEL_PHOTO_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;


--客服站点关联表
CREATE TABLE TRAVEL_SERVICESITE(
	SZ_ID			NUMBER(20)		NOT NULL,	--主键编号
	GMT_CREATE		TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED	TIMESTAMP		NOT NULL,	--修改时间
	
	S_ID            NUMBER(20),      	--客服ID
	Z_ID            NUMBER(20)      	--专线ID
);
ALTER TABLE TRAVEL_SERVICESITE ADD CONSTRAINT PK_TRAVEL_SERVICESITE PRIMARY KEY(SZ_ID);
CREATE SEQUENCE TRAVEL_SERVICESITE_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--操作日志表
CREATE TABLE TRAVEL_OPERATIONLOG(
	OL_ID			NUMBER(20)		NOT NULL,	--主键编号
	GMT_CREATE		TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED	TIMESTAMP		NOT NULL,	--修改时间
	OL_TABLE		VARCHAR2(200),		--表名
	OL_TABLEPK      NUMBER(20),      	--相关表主键值
	OL_TABLEPB      VARCHAR2(200),      --相关表候选列值
	OL_CHANGEBEFORE	NCLOB,		--修改前值
	OL_CHANGELATER	NCLOB,		--修改后值
	M_ID			NUMBER(20),			--操作人
	C_ID			NUMBER(20),			--所属公司
	OL_CORRMODULE	VARCHAR2(500)		--相关模块
);
ALTER TABLE TRAVEL_OPERATIONLOG ADD CONSTRAINT PK_TRAVEL_OPERATIONLOG PRIMARY KEY(OL_ID);
CREATE SEQUENCE TRAVEL_OPERATIONLOG_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

--标签类目表
CREATE TABLE TRAVEL_LABELCATEGORY(
	LC_ID			NUMBER(20)		NOT NULL,	--主键编号
	GMT_CREATE		TIMESTAMP		NOT NULL,	--创建时间
	GMT_MODIFIED	TIMESTAMP		NOT NULL,	--修改时间
	PARENT_ID		NUMBER(20),			--父类ID
	LC_NAME      	VARCHAR2(500),  	--名称
	LC_TYPE      	NUMBER(9),      	--热门类型
	S_ID			NUMBER(20),			--站点ID
	PROVINCE 		VARCHAR2(200),		--省名
	CITY 			VARCHAR2(200),		--城市名
	LINE_TYPE		NUMBER(9),			--线路类型
	LC_SORT			NUMBER(20)			--排序
);
ALTER TABLE TRAVEL_LABELCATEGORY ADD CONSTRAINT PK_TRAVEL_LABELCATEGORY PRIMARY KEY(LC_ID);
CREATE SEQUENCE TRAVEL_LABELCATEGORY_SEQ INCREMENT BY 1 START WITH 1 NOMAXVALUE;

------------------------------------------------------------------创建表--END-----------------------------------------------------------------
