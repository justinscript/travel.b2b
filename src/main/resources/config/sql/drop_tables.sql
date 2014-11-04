--select 'exec dropifexists_table(''' || table_name ||''');' from user_tables;
--select 'exec dropifexists_seq(''' || SEQUENCE_NAME ||''');' from user_sequences;
-------------------------------------------------------------------------------------
--
--	左边网数据库创建脚本
--	
--  删除表(table),序列(Sequence)
--
-------------------------------------------------------------------------------------

-----------------------------删除左边网的表--BEGIN--------------------------------------   
                 
exec dropifexists_table('TRAVEL_COMPANY');                      
exec dropifexists_table('TRAVEL_MEMBER');
exec dropifexists_table('TRAVEL_SERVICE');
exec dropifexists_table('TRAVEL_BLACKLIST');

exec dropifexists_table('TRAVEL_LINE');
exec dropifexists_table('TRAVEL_ROUTE');
exec dropifexists_table('TRAVEL_TRAFFIC');

exec dropifexists_table('TRAVEL_ORDER');
exec dropifexists_table('TRAVEL_ORDERGUEST'); 

exec dropifexists_table('TRAVEL_SITE');
exec dropifexists_table('TRAVEL_COLUMN');
exec dropifexists_table('TRAVEL_LINECOLUMN'); 

exec dropifexists_table('TRAVEL_GIFT');
exec dropifexists_table('TRAVEL_GIFTCLASS');
exec dropifexists_table('TRAVEL_INTEGRALDEAL');
exec dropifexists_table('TRAVEL_GIFTORDER');
exec dropifexists_table('TRAVEL_INTEGRAL');

exec dropifexists_table('TRAVEL_FINANCE');
exec dropifexists_table('TRAVEL_FINANCE_DETAIL');

exec dropifexists_table('TRAVEL_NEWS');
exec dropifexists_table('TRAVEL_ARTICLES');
exec dropifexists_table('TRAVEL_ADVERTISEMENT');

exec dropifexists_table('TRAVEL_MESSAGE');

exec dropifexists_table('TRAVEL_PHOTO');

-----------------------------删除左边网的表--END--------------------------------------

-----------------------------删除左边网的序列--BEGIN-----------------------------------

exec dropifexists_seq('TRAVEL_COMPANY_SEQ');
exec dropifexists_seq('TRAVEL_MEMBER_SEQ');
exec dropifexists_seq('TRAVEL_SERVICE_SEQ');
exec dropifexists_seq('TRAVEL_BLACKLIST_SEQ');

exec dropifexists_seq('TRAVEL_LINE_SEQ');
exec dropifexists_seq('TRAVEL_ROUTE_SEQ'); 
exec dropifexists_seq('TRAVEL_TRAFFIC_SEQ'); 

exec dropifexists_seq('TRAVEL_ORDER_SEQ');
exec dropifexists_seq('TRAVEL_ORDERGUEST_SEQ');

exec dropifexists_seq('TRAVEL_SITE_SEQ');                    
exec dropifexists_seq('TRAVEL_COLUMN_SEQ');
exec dropifexists_seq('TRAVEL_LINECOLUMN_SEQ');

exec dropifexists_seq('TRAVEL_GIFT_SEQ');
exec dropifexists_seq('TRAVEL_GIFTCLASS_SEQ');
exec dropifexists_seq('TRAVEL_INTEGRALDEAL_SEQ');
exec dropifexists_seq('TRAVEL_GIFTORDER_SEQ');
exec dropifexists_seq('TRAVEL_INTEGRAL_SEQ');

exec dropifexists_seq('TRAVEL_NEWS_SEQ');
exec dropifexists_seq('TRAVEL_ARTICLES_SEQ');
exec dropifexists_seq('TRAVEL_ADVERTISEMENT_SEQ');

exec dropifexists_seq('TRAVEL_FINANCE_SEQ');
exec dropifexists_seq('TRAVEL_FINANCE_DETAIL_SEQ');

exec dropifexists_seq('TRAVEL_MESSAGE_SEQ');

exec dropifexists_seq('TRAVEL_PHOTO_SEQ');

-----------------------------删除左边网的序列--END-----------------------------------

-----------------------------调用存储过程删除表-------------------------------------

----------------------------删除左边网的存储过程-------------------------------------
--dropifexists_procedure('DROPIFEXISTS_SEQ');
--dropifexists_procedure('DROPIFEXISTS_TABLE');
--dropifexists_procedure('DROPIFEXISTS_INDEX');
drop PROCEDURE DROPIFEXISTS_SEQ;
drop PROCEDURE DROPIFEXISTS_TABLE;
drop PROCEDURE DROPIFEXISTS_INDEX;