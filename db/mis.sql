/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/11/13 12:18:28                          */
/*==============================================================*/

use mis;
set character set utf8;

/*==============================================================*/
/* Table: t_area                                                */
/*==============================================================*/
create table t_area
(
   f_area_id            int not null,
   f_code               varchar(50) comment '区域代码',
   f_name               varchar(50),
   primary key (f_area_id)
);

/*==============================================================*/
/* Table: t_brand                                               */
/*==============================================================*/
create table t_brand
(
   f_brand_id           int not null,
   f_brand_name         varchar(50),
   primary key (f_brand_id)
);

/*==============================================================*/
/* Table: t_daily_reminder                                      */
/*==============================================================*/
create table t_daily_reminder
(
   f_operator_id        int not null,
   f_content            varchar(500),
   f_reminder_time      datetime,
   f_read_time          datetime,
   primary key (f_operator_id)
);

/*==============================================================*/
/* Table: t_level                                               */
/*==============================================================*/
create table t_level
(
   f_level_id           int not null,
   f_level_name         varchar(50),
   primary key (f_level_id)
);

alter table t_level comment '一级代理商
二级代理商
三级代理商';

/*==============================================================*/
/* Table: t_model                                               */
/*==============================================================*/
create table t_model
(
   f_model_id           int not null,
   f_brand_id           int,
   f_model_name         varchar(50),
   primary key (f_model_id)
);

/*==============================================================*/
/* Table: t_operator                                            */
/*==============================================================*/
create table t_operator
(
   f_operator_id        int not null,
   f_name               varchar(50),
   f_login              varchar(50),
   f_password           varchar(50),
   f_created_time       datetime,
   f_updated_time       datetime,
   f_inactive           int,
   primary key (f_operator_id)
);

/*==============================================================*/
/* Index: login_index                                           */
/*==============================================================*/
create unique index login_index on t_operator
(
   f_login
);

/*==============================================================*/
/* Table: t_os                                                  */
/*==============================================================*/
create table t_os
(
   f_os_id              int not null,
   f_os_name            varchar(50),
   primary key (f_os_id)
);

/*==============================================================*/
/* Table: t_pay_condition                                       */
/*==============================================================*/
create table t_pay_condition
(
   f_pay_condition_id   int not null,
   f_pay_condition_name varchar(50),
   f_remark             varchar(500),
   primary key (f_pay_condition_id)
);

alter table t_pay_condition comment '付款条件';

/*==============================================================*/
/* Table: t_product                                             */
/*==============================================================*/
create table t_product
(
   f_product_id         int not null,
   f_code               varchar(50),
   f_product_name       varchar(50),
   f_os_id              int,
   f_model_id           int,
   f_vendor_id          int,
   f_brand_id           int,
   f_operator_id        int,
   f_price              decimal(9,2),
   f_features           varchar(2000),
   f_remark             varchar(400),
   f_created_time       datetime,
   f_updated_time       datetime,
   f_deleted            int,
   primary key (f_product_id)
);

/*==============================================================*/
/* Index: code_index                                            */
/*==============================================================*/
create unique index code_index on t_product
(
   f_code
);

/*==============================================================*/
/* Table: t_vendor                                              */
/*==============================================================*/
create table t_vendor
(
   f_vendor_id          int not null,
   f_area_id            int,
   f_level_id           int,
   f_vendor_name        varchar(50),
   f_parent_key         varchar(200),
   f_parent_id          int,
   f_pay_condition_id   int,
   f_operator_id        int,
   f_created_time       datetime,
   f_updated_time       datetime,
   f_inactive           int,
   primary key (f_vendor_id)
);

/*==============================================================*/
/* Table: tr_data_function                                      */
/*==============================================================*/
create table tr_data_function
(
   f_data_function_id   int not null,
   f_role_id            int,
   f_table_code         varchar(50),
   f_actions            varchar(200),
   f_id                 int,
   primary key (f_data_function_id)
);

/*==============================================================*/
/* Index: dp_index                                              */
/*==============================================================*/
create unique index dp_index on tr_data_function
(
   f_table_code,
   f_id
);

/*==============================================================*/
/* Table: tr_operator_role                                      */
/*==============================================================*/
create table tr_operator_role
(
   f_role_operator_id   int not null auto_increment,
   f_operator_id        int not null,
   f_role_id            int not null,
   primary key (f_role_operator_id)
);

/*==============================================================*/
/* Table: tr_privilege                                          */
/*==============================================================*/
create table tr_privilege
(
   f_privilege_id       int not null,
   f_role_id            int not null,
   f_function_id        int not null,
   f_readonly           int,
   primary key (f_privilege_id)
);

/*==============================================================*/
/* Index: privilege_index                                       */
/*==============================================================*/
create unique index privilege_index on tr_privilege
(
   f_role_id,
   f_function_id
);

/*==============================================================*/
/* Table: ts_function                                           */
/*==============================================================*/
create table ts_function
(
   f_function_id        int not null,
   f_name               varchar(50),
   f_action_url         varchar(100) comment '/admin/vendor/list',
   primary key (f_function_id)
);

/*==============================================================*/
/* Index: url_index                                             */
/*==============================================================*/
create unique index url_index on ts_function
(
   f_action_url
);

/*==============================================================*/
/* Table: ts_generator                                          */
/*==============================================================*/
create table ts_generator
(
   f_generator_id       int not null auto_increment,
   f_generator_name     varchar(50),
   f_value              int,
   primary key (f_generator_id)
);

/*==============================================================*/
/* Table: ts_role                                               */
/*==============================================================*/
create table ts_role
(
   f_role_id            int not null,
   f_code               varchar(50),
   f_role_name          varchar(50),
   primary key (f_role_id)
);

/*==============================================================*/
/* Index: rc_index                                              */
/*==============================================================*/
create unique index rc_index on ts_role
(
   f_code
);

alter table t_daily_reminder add constraint FK_Reference_20 foreign key (f_operator_id)
      references t_operator (f_operator_id) on delete restrict on update restrict;

alter table t_model add constraint FK_Reference_1 foreign key (f_brand_id)
      references t_brand (f_brand_id) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_14 foreign key (f_operator_id)
      references t_operator (f_operator_id) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_2 foreign key (f_os_id)
      references t_os (f_os_id) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_3 foreign key (f_model_id)
      references t_model (f_model_id) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_4 foreign key (f_vendor_id)
      references t_vendor (f_vendor_id) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_5 foreign key (f_brand_id)
      references t_brand (f_brand_id) on delete restrict on update restrict;

alter table t_vendor add constraint FK_Reference_21 foreign key (f_pay_condition_id)
      references t_pay_condition (f_pay_condition_id) on delete restrict on update restrict;

alter table t_vendor add constraint FK_Reference_22 foreign key (f_operator_id)
      references t_operator (f_operator_id) on delete restrict on update restrict;

alter table t_vendor add constraint FK_Reference_6 foreign key (f_area_id)
      references t_area (f_area_id) on delete restrict on update restrict;

alter table t_vendor add constraint FK_Reference_7 foreign key (f_level_id)
      references t_level (f_level_id) on delete restrict on update restrict;

alter table t_vendor add constraint FK_Reference_8 foreign key (f_parent_id)
      references t_vendor (f_vendor_id) on delete restrict on update restrict;

alter table tr_data_function add constraint FK_Reference_17 foreign key (f_role_id)
      references ts_role (f_role_id) on delete restrict on update restrict;

alter table tr_operator_role add constraint FK_Reference_18 foreign key (f_operator_id)
      references t_operator (f_operator_id) on delete restrict on update restrict;

alter table tr_operator_role add constraint FK_Reference_19 foreign key (f_role_id)
      references ts_role (f_role_id) on delete restrict on update restrict;

alter table tr_privilege add constraint FK_Reference_13 foreign key (f_role_id)
      references ts_role (f_role_id) on delete restrict on update restrict;

alter table tr_privilege add constraint FK_Reference_15 foreign key (f_function_id)
      references ts_function (f_function_id) on delete restrict on update restrict;

