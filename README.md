#计费管理
这个模块是智慧社区里的计费模块。
##API
Api接口

##数据表


### 缴费记录对应数据库表说明书

| No                 | 数据库名称         | 数据库表名称(英文)           | 数据库表名称(中文)                             | 备注                                   |
| :----------------- | :---------------- | :-------------------------- | :-------------------------------------------- | :------------------------------------- |
| 1	                 | fm                |	[payment_order](#payment_order)	           | 缴费订单表                                     |	用于记录订单生成                     |
| 2	                 | fm	             |  payment_record	           | 缴费记录表                                     |	用于记录支付时间并关联订单表和记录表  |
| 3	                 | fm	             |  payment_state	           | 交易状态表                                     |	用于记录交易状态                     |
| 4	                 | fm	             |  payment_log	               | 订单日志表                                     |	用于记录订单日志                     |
| 5	                 | fm	             |  park_payment_order         | 停车缴费订单子表                                |	用于记录停车缴费订单信息              |
| 6	                 | fm	             |  payment_syn_exception_log  | 缴费记录同步异常记录表                          |	用于缴费记录同步异常                  |
| 7	                 | fm	             |  reconciliation_diff_record | 对账差异记录表                                 |	对账差异记录表                        |
| 8	                 | fm	             |  nc_bill                    | 物业费账单表                                   |	物业费账单表                          |
| 9	                 | fm	             |  etl_batch                  | 小区批次执行表                                 |	                                    |
| 10	             | fm	             |  etl_task                   | 任务表                                         |	                                    |
| 11	             | fm	             |  etl_task_execute           | 主任务执行记录                                 |	                                    |
| 12	             | fm	             |  etl_task_type              | 任务类型表                                     |	                                    |
| 13	             | fm	             |  nc_faretype                | NC物业类型表                                   |	                                    |
| 14	             | fm	             |  fm_erp_faretype            | ERP费用类型表                                  |	                                    |
| 15	             | fm	             |  fm_erp_record              | ERP费用明细表                                  |	                                    |
| 16	             | fm	             |  fm_platform_type           | 对接系统记录表                                 |	                                    |
| 17	             | fm	             |  fm_payment_sub_order       | 子订单表                                       |	用于预缴                             |
| 18	             | fm	             |  fm_temp_park_record        | ERP临停账单表                                  |	                                    |

###payment_order
