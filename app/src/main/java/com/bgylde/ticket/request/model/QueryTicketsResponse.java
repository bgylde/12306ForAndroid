package com.bgylde.ticket.request.model;

import java.util.List;

/**
 * Created by wangyan on 2019/1/8
 */
public class QueryTicketsResponse {
    // {
    //    "data": {
    //        "flag": "1",
    //        "map": {
    //            "BJP": "北京",
    //            "BXP": "北京西",
    //            "TDV": "太原东",
    //            "TNV": "太原南",
    //            "TYV": "太原"
    //        },
    //        "result": [
    //            "|预订|24000K40970H|K4097|BJP|LFV|BJP|TDV|01:46|12:39|10:53|N|Lfc5isgG5TSyW6yWk5eQJqSLd7mM7ZQFRqDLPOJPMoys9m7MjlHd92DuHwk%3D|20190201|3|PC|01|09|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|120000K96020|K961|SBT|YNV|BJP|TDV|02:54|13:30|10:36|N|%2FO%2BaUbUkY7zPEbXPUo9OVt%2FnfyRcy%2F0uq%2BL0OgB8CkU06D261BSA1G2iB%2FI%3D|20190131|3|T2|13|22|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|240000G6010A|G601|BXP|TNV|BXP|TNV|07:21|10:22|03:01|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G62705|G627|BXP|ABV|BXP|TNV|08:05|11:16|03:11|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|2400000G910B|G91|BXP|TNV|BXP|TNV|08:40|11:07|02:27|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|02|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|2400000Z690K|Z69|BXP|WAR|BXP|TNV|10:00|15:11|05:11|N|Tvb9bTIKePelcqvMR4TwN62beaaCV7m4GsuPwMSiYYcdiMPDXAOZqmyq4t0%3D|20190201|3|P4|01|05|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|240000G6030D|G603|BXP|TNV|BXP|TNV|10:10|12:55|02:45|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|04|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G6050B|G605|BXP|AJV|BXP|TNV|10:28|13:24|02:56|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G60709|G607|BXP|TNV|BXP|TNV|11:14|14:05|02:51|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G60908|G609|BXP|TNV|BXP|TNV|11:33|14:25|02:52|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G6110M|G611|BXP|TNV|BXP|TNV|12:38|15:38|03:00|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|04|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|24000K11150P|K1115|BXP|BTC|BXP|TYV|12:52|19:24|06:32|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|PB|01|06|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|240000T1750I|T175|BXP|XNO|BXP|TYV|13:05|18:23|05:18|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P4|01|04|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|24000D20030H|D2003|BXP|ABV|BXP|TNV|13:16|16:45|03:29|N|UZ7X7FXU%2BP61f4zLluO%2FSgR%2FUwrU8oFdXNZunVSRVMpSvQrH|20190201|3|P3|01|06|1|0|||||||无||||无|无|||O0M0O0|OMO|0|0",
    //            "|预订|240000G6130F|G613|BXP|TNV|BXP|TNV|14:10|17:06|02:56|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|2400000T410V|T41|BXP|XAY|BXP|TYV|14:22|19:46|05:24|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P2|01|04|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|2400000Z550M|Z55|BXP|LZJ|BXP|TYV|14:58|19:55|04:57|N|FotUEM4V2XZD3hu6ygq2ynCNUMQKH%2BUPGZH3jkF8XqPD%2FtMv|20190201|3|P4|01|04|0|0||无||无|||||无||||||604030|643|0|0",
    //            "|预订|240000G6150C|G615|BXP|TNV|BXP|TNV|15:03|18:04|03:01|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|24000D20050G|D2005|BXP|LXV|BXP|TNV|15:29|18:56|03:27|N|UZ7X7FXU%2BP61f4zLluO%2FSgR%2FUwrU8oFdXNZunVSRVMpSvQrH|20190201|3|P4|01|06|1|0|||||||无||||无|无|||O0M0O0|OMO|0|0",
    //            "|预订|24000000T714|T7|BXP|CDW|BXP|TYV|16:40|22:07|05:27|N|VYljTchUWNfIZO31k9Jnz0vgtrMuZMUKR5YduYCV%2BfMyEuGctbyFN7Bhrow%3D|20190201|3|P3|01|04|0|0||||无|||无||无|无|||||10403010|1431|0|0",
    //            "|预订|240000G68300|G683|BXP|IPV|BXP|TNV|16:58|19:42|02:44|N|8eaj4lKtFt5NWRTTajOuN%2FFk2JusdRFHDMovL0OZvSu5Sypt|20190201|3|P3|01|04|1|0|||||||||||无|无|无||O090M0|O9M|0|0",
    //            "|预订|240000K6030O|K603|BJP|YNV|BJP|TYV|17:12|03:27|10:15|N|Lfc5isgG5TSyW6yWk5eQJqSLd7mM7ZQFRqDLPOJPMoys9m7MjlHd92DuHwk%3D|20190201|3|PA|01|10|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|240000G61905|G619|BXP|TNV|BXP|TNV|18:02|20:53|02:51|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G62105|G621|BXP|TNV|BXP|TNV|18:07|21:10|03:03|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P2|01|06|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000G62306|G623|BXP|TNV|BXP|TNV|18:45|21:50|03:05|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P3|01|05|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|2400000Z210C|Z21|BXP|LSO|BXP|TYV|20:00|00:19|04:19|N|%2FE0zQ4Fqe6WU%2B6NQ1gdbxJZZMS6EvlV2EOsFfoORYKYpvImszsOgHJyG2k8%3D|20190201|3|P2|01|03|0|0||||无|||无||无|无|||||10401030|1413|0|0",
    //            "|预订|240000G62507|G625|BXP|TNV|BXP|TNV|20:05|22:37|02:32|N|9shlQgryYHHR0SGxQuYdbdXfcFEkPxltlAiCtD2xRaP1gCJM|20190201|3|P4|01|03|1|0|||||||||||无|无|无||O0M090|OM9|0|0",
    //            "|预订|240000Z27708|Z277|BXP|YIJ|BXP|TYV|20:06|00:28|04:22|N|im74%2FM0nowkaI9jhPDSy83XChfMYNdvGISnAPxHf6cnoAnFRXln4IGg0EWrWQR35Pa%2FDqg2fT58%3D|20190201|3|P3|01|03|0|0||无||无|||无||无|无|||||1040106030|14163|0|0",
    //            "|预订|240000K6010K|K601|BJP|TYV|BJP|TDV|22:21|08:10|09:49|N|i6EBvmgLjHSCU95%2FvElBrDHHoJzrXsfw8FoK75VdiK%2BnrN%2BWQS4Op17tYkB87BK9WhwiP%2BHT1Co%3D|20190201|3|PA|01|09|0|0||无||无|||无||无|无|||||1040106030|14163|0|0",
    //            "|预订|240000K6090C|K609|BJP|HCY|BJP|TYV|23:55|07:37|07:42|N|8tge3Tsc1%2B0miIf9mTe4r3m0wG0ZCyVdzaLeqY29Lg%2BhOoh4rOMMkufogvc%3D|20190201|3|PA|01|08|0|0||||无|||无||无|无|||||10401030|1413|0|0"
    //        ]
    //    },
    //    "httpstatus": 200,
    //    "messages": "",
    //    "status": true
    //}

    private Data data;
    private int httpstatus;
    private String messages;
    private boolean status;

    public Data getData() {
        return data;
    }

    public int getHttpstatus() {
        return httpstatus;
    }

    public String getMessages() {
        return messages;
    }

    public boolean getStatus() {
        return status;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public void setHttpstatus(int httpstatus) {
        this.httpstatus = httpstatus;
    }

    public class Data {
        private String flag;
        private List<String> result;

        public List<String> getResult() {
            return result;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
