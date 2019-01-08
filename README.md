# 1、请求抢票页面 
```
curl -H 'Referer:https://kyfw.12306.cn/otn/login/init' -H 'Host:kyfw.12306.cn' https://kyfw.12306.cn/otn/leftTicket/init
此时返回cookies，记录用于之后的请求
```
# 2、登录账号

## a. 下载验证码
```
curl -H 'Referer:https://kyfw.12306.cn/otn/login/init' -H 'Host:kyfw.12306.cn' https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&35
```
 ![avatar][AuthCode.png]
## b. 认证 带cookie
```
curl -H 'Referer:https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "appid=otn" https://kyfw.12306.cn/passport/web/auth/uamtk
```
## c. 验证码校验 带cookie
```
curl -H 'Referer:https://kyfw.12306.cn/otn/login/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "answer=46,42,46,105,45,184,48,256,36,117,112,112,114,181,111,252&rand=sjrand&login_site=E" https://kyfw.12306.cn/passport/captcha/captcha-check
```
## d. 检查用户登录 带cookie
```
curl -H 'Referer:https://kyfw.12306.cn/otn/leftTicket/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "_json_att=" https://kyfw.12306.cn/otn/login/checkUser
```
# 3、循环查询剩余票数（定时检查用户登录状态）
## a. 检查是否是12306休息时间 23：00--06：00休息
## b. 查询余票 带cookie，也需要cookie来设置query url
```
curl -H 'Referer:https://kyfw.12306.cn/otn/leftTicket/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" https://kyfw.12306.cn/otn/{3}              ?leftTicketDTO.train_date={0}       &leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT
                                                                                                                                                                                                                                                                                                                                                                               e.g. https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2019-02-01&leftTicketDTO.from_station=BXP&leftTicketDTO.to_station=TNV&purpose_codes=ADULT
           response：
           {
              "data": {
                  "flag": "1",
                  "map": {
                  "BJP": "北京",
                  "BXP": "北京西",
                  "TDV": "太原东",
                  "TNV": "太原南",
                  "TYV": "太原"
                  },
                  "result": [
                      "|预订|24000K40970H|K4097|BJP|LFV|BJP|TDV|01:46|12:39|10:53|N|Lfc5isgG5TSyW6yWk5eQJqSLd7mM7ZQFRqDLPOJPMoys9m7MjlHd92DuHwk%3D|20190201|3|PC|01|09|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|120000K96020|K961|SBT|YNV|BJP|TDV|02:54|13:30|10:36|N|%2FO%2BaUbUkY7zPEbXPUo9OVt%2FnfyRcy%2F0uq%2BL0OgB8CkU06D261BSA1G2iB%2FI%3D|20190131|3|T2|13|22|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000G6010A|G601|BXP|TNV|BXP|TNV|07:21|10:22|03:01|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G62705|G627|BXP|ABV|BXP|TNV|08:05|11:16|03:11|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|2400000G910B|G91|BXP|TNV|BXP|TNV|08:40|11:07|02:27|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|02|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|2400000Z690K|Z69|BXP|WAR|BXP|TNV|10:00|15:11|05:11|N|Tvb9bTIKePelcqvMR4TwN62beaaCV7m4GsuPwMSiYYcdiMPDXAOZqmyq4t0%3D|20190201|3|P4|01|05|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000G6030D|G603|BXP|TNV|BXP|TNV|10:10|12:55|02:45|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|04|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G6050B|G605|BXP|AJV|BXP|TNV|10:28|13:24|02:56|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G60709|G607|BXP|TNV|BXP|TNV|11:14|14:05|02:51|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G60908|G609|BXP|TNV|BXP|TNV|11:33|14:25|02:52|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G6110M|G611|BXP|TNV|BXP|TNV|12:38|15:38|03:00|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|04|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|24000K11150P|K1115|BXP|BTC|BXP|TYV|12:52|19:24|06:32|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|PB|01|06|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000T1750I|T175|BXP|XNO|BXP|TYV|13:05|18:23|05:18|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P4|01|04|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|24000D20030H|D2003|BXP|ABV|BXP|TNV|13:16|16:45|03:29|N|UZ7X7FXU%2BP61f4zLluO%2FSgR%2FUwrU8oFdXNZunVSRVMpSvQrH|20190201|3|P3|01|06|1|0|||||||无||||无|无|||O0M0O0|OMO|0|0",
                      "|预订|240000G6130F|G613|BXP|TNV|BXP|TNV|14:10|17:06|02:56|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|2400000T410V|T41|BXP|XAY|BXP|TYV|14:22|19:46|05:24|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P2|01|04|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|2400000Z550M|Z55|BXP|LZJ|BXP|TYV|14:58|19:55|04:57|N|FotUEM4V2XZD3hu6ygq2ynCNUMQKH%2BUPGZH3jkF8XqPD%2FtMv|20190201|3|P4|01|04|0|0||无||无|||||无||||||604030|643|0|0",
                      "|预订|240000G6150C|G615|BXP|TNV|BXP|TNV|15:03|18:04|03:01|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|24000D20050G|D2005|BXP|LXV|BXP|TNV|15:29|18:56|03:27|N|UZ7X7FXU%2BP61f4zLluO%2FSgR%2FUwrU8oFdXNZunVSRVMpSvQrH|20190201|3|P4|01|06|1|0|||||||无||||无|无|||O0M0O0|OMO|0|0",
                      "|预订|24000000T714|T7|BXP|CDW|BXP|TYV|16:40|22:07|05:27|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P3|01|04|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000G68300|G683|BXP|IPV|BXP|TNV|16:58|19:42|02:44|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|04|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000K6030O|K603|BJP|YNV|BJP|TYV|17:12|03:27|10:15|N|Lfc5isgG5TSyW6yWk5eQJqSLd7mM7ZQFRqDLPOJPMoys9m7MjlHd92DuHwk%3D|20190201|3|PA|01|10|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000G61905|G619|BXP|TNV|BXP|TNV|18:02|20:53|02:51|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G62105|G621|BXP|TNV|BXP|TNV|18:07|21:10|03:03|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000G62306|G623|BXP|TNV|BXP|TNV|18:45|21:50|03:05|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|2400000Z210C|Z21|BXP|LSO|BXP|TYV|20:00|00:19|04:19|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P2|01|03|0|0||||无|||无||无|无|||||10401030|1413|0|0",
                      "|预订|240000G62507|G625|BXP|TNV|BXP|TNV|20:05|22:37|02:32|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|03|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
                      "|预订|240000Z27708|Z277|BXP|YIJ|BXP|TYV|20:06|00:28|04:22|N|im74%2FM0nowkaI9jhPDSy83XChfMYNdvGISnAPxHf6cnoAnFRXln4IGg0EWrWQR35Pa%2FDqg2fT58%3D|20190201|3|P3|01|03|0|0||无||无|||无||无|无|||||1040106030|14163|0|0",
                      "|预订|240000K6010K|K601|BJP|TYV|BJP|TDV|22:21|08:10|09:49|N|i6EBvmgLjHSCU95%2FvElBrDHHoJzrXsfw8FoK75VdiK%2BnrN%2BWQS4Op17tYkB87BK9WhwiP%2BHT1Co%3D|20190201|3|PA|01|09|0|0||无||无|||无||无|无|||||1040106030|14163|0|0",
                      "|预订|240000K6090C|K609|BJP|HCY|BJP|TYV|23:55|07:37|07:42|N|qilH4A34PHDvhYGcXSJ0tnJZWdgBDNzuV4MiB576PFuUVsH9eXuxwjdstTo%3D|20190201|3|PA|01|08|0|0||||无|||无||无|无|||||10403010|1431|0|0"
                  ]
              },
              "httpstatus": 200,
              "messages": "",
              "status": true
          }
```
# 4、获取联系人： 带cookies
```
curl -H 'Referer:https://kyfw.12306.cn/otn/confirmPassenger/initDc' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "" https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs
```
# 5、提交订单： 带cookies
## a. 快速下单：
### 1. 下单
```
curl -H 'Referer:https://kyfw.12306.cn/otn/leftTicket/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "secretStr={0}&train_date={1}&tour_flag=dc&purpose_codes=ADULT&query_from_station_name={2}&query_to_station_name={3}&cancel_flag=2&bed_level_order_num=000000000000000000000000000000&passengerTicketStr={4}&oldPassengerStr={5}" https://kyfw.12306.cn/otn/confirmPassenger/autoSubmitOrderRequest
```
### 2. 验证码提交验证
### 3. 请求排队接口
```
curl -H 'Referer:https://kyfw.12306.cn/otn/leftTicket/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "train_date={0}&train_no={1}&stationTrainCode={2}&seatType={3}&fromStationTelecode={4}&toStationTelecode={5}&leftTicket={6}&purpose_codes=ADULT&_json_att=" https://kyfw.12306.cn/otn/confirmPassenger/getQueueCountAsync
```
## b. 普通下单：
### 1. 下单
```
curl -H 'Referer:https://kyfw.12306.cn/otn/leftTicket/init' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "secretStr={0}&train_date={1}&back_train_date={2}&tour_flag=dc&purpose_codes=ADULT&query_from_station_name={3}&query_to_station_name={4}" https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest
```
### 2. 检查支付订单(检查订单信息规范)
```
curl -H 'Referer:https://kyfw.12306.cn/otn/confirmPassenger/initDc' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "passengerTicketStr={0}&oldPassengerStr={2}&REPEAT_SUBMIT_TOKEN={3}&randCode=&cancel_flag=2&bed_level_order_num=000000000000000000000000000000&tour_flag=dc&_json_att=" https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo
```
### 3. 排队
```
curl -H 'Referer:https://kyfw.12306.cn/otn/confirmPassenger/initDc' -H 'Host:kyfw.12306.cn' -b "JSESSIONID=DA1413B17835754D9E62E23F073E12C3;route=6f50b51faa11b987e576cdb301e545c4;BIGipServerotn=1290797578.50210.0000;BIGipServerpool_passport=166527498.50215.0000;_passport_session=66b5b00eb46149caa3da585edc8e25ee8178;_passport_ct=375389e6d3754cdabdcb1684556812cdt0304" -d "train_date={0}&train_no={1}&stationTrainCode={2}&seatType={3}&fromStationTelecode={4}&toStationTelecode={5}&leftTicket={6}&purpose_codes={7}&train_location={8}&REPEAT_SUBMIT_TOKEN={9}" https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount
```
[AuthCode.png]:data:image/png;base64,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
