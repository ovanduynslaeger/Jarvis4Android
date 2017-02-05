package com.automation.jarvis.back;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.automation.jarvis.object.Automation;
import com.automation.jarvis.object.Category;
import com.automation.jarvis.object.Control;
import com.automation.jarvis.object.Device;
import com.automation.jarvis.object.Info;
import com.automation.jarvis.object.Location;
import com.automation.jarvis.util.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by olivierv on 05/01/17.
 */

public class AutomationGatewayApi {

    private static AutomationGatewayApi mInstance;
    private Context mCtx;
    private Automation mAuto = new Automation();

    private AutomationGatewayApi(Context context) {
        mCtx = context;
    }

    public Automation getAutomation() {
        return mAuto;
    }

    public static synchronized AutomationGatewayApi getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AutomationGatewayApi(context);
            mInstance.refresh();
        }
        return mInstance;
    }

    private static String JEEDOM_API_PREFIX = "http://192.168.0.4/core/api/myJeeApi.php?";
    private static String JEEDOM_API_KEY = "ubgd1fy5u3l9cb7uftex";
    private static String JEEDOM_API_CMDREQUESTPATTERN = "request={\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"cmd::execCmd\",\"params\":{\"apikey\":\"@APIKEY\",\"id\":\"@ID\"}}";
    private static String JEEDOM_API_SLICERREQUESTPATTERN = "request={\"jsonrpc\":\"2.0\",\"method\":\"cmd::execCmd\",\"params\":{\"apikey\":\"@APIKEY\",\"id\": \"@ID\", \"options\":{\"slider\": \"@SLIDER\"}}}";
    private static String JEEDOM_API_GETDEVICES = "request={\"jsonrpc\":\"2.0\",\"method\":\"object::fullEq\",\"params\":{\"apikey\":\"@APIKEY\"}}";



    public String sendCmd(Control ctrl) {
        String url="";
        if (ctrl.getStyle().equals(Control.STYLE_SLIDER)) {
            url = getUrl(JEEDOM_API_SLICERREQUESTPATTERN,ctrl.getId(),ctrl.getValue());
        } else {
            url = getUrl(JEEDOM_API_CMDREQUESTPATTERN,ctrl.getId());
        }
        Log.d(this.getClass().getName(),url);
        String ret = gatewayRequestCmd(url);
        if (ctrl.isToAdvertise()) {
            Toast toast = Toast.makeText(mCtx, ctrl.getName() + "/" + ctrl.getValue(), Toast.LENGTH_LONG );
            toast.show();
        }
        return ret;
    }

    private String getUrl(String pattern) {
        return JEEDOM_API_PREFIX + pattern.replace("@APIKEY",JEEDOM_API_KEY);
    }

    private String getUrl(String pattern, String cmdId, int slideValue) {
        String url = getUrl(pattern,cmdId);
        url = url.replace("@SLIDER",Integer.toString(slideValue));
        return url;
    }
    private String getUrl(String pattern, String cmdId) {
        String url = JEEDOM_API_PREFIX + pattern.replace("@ID",cmdId);
        url = url.replace("@APIKEY",JEEDOM_API_KEY);
        return url;
    }

    private JSONObject getDevicesMoke() {
      String json;
      json ="{\n" +
              "    \"jsonrpc\":\"2.0\",\n" +
              "    \"id\":null,\n" +
              "    \"result\":[\n" +
              "        {\n" +
              "            \"id\":\"8\",\n" +
              "            \"name\":\"Bureau\",\n" +
              "            \"father_id\":\"7\",\n" +
              "            \"isVisible\":\"1\",\n" +
              "            \"position\":\"8\",\n" +
              "            \"configuration\":null,\n" +
              "            \"display\":{\n" +
              "                \"icon\":\"\",\n" +
              "                \"tagColor\":\"#9b59b6\",\n" +
              "                \"tagTextColor\":\"#ffffff\"\n" +
              "            },\n" +
              "            \"eqLogics\":[\n" +
              "                {\n" +
              "                    \"id\":\"127\",\n" +
              "                    \"name\":\"Volet bureau\",\n" +
              "                    \"logicalId\":\"\",\n" +
              "                    \"object_id\":\"8\",\n" +
              "                    \"eqType_name\":\"rcswitch\",\n" +
              "                    \"eqReal_id\":null,\n" +
              "                    \"isVisible\":\"1\",\n" +
              "                    \"isEnable\":\"1\",\n" +
              "                    \"configuration\":{\n" +
              "                        \"createtime\":\"2016-05-22 19:17:20\",\n" +
              "                        \"updatetime\":\"2016-05-22 19:18:59\",\n" +
              "                        \"battery_type\":\"\",\n" +
              "                        \"batteryStatusDatetime\":\"\",\n" +
              "                        \"batteryStatus\":\"\",\n" +
              "                        \"battery_danger_threshold\":\"\",\n" +
              "                        \"battery_warning_threshold\":\"\"\n" +
              "                    },\n" +
              "                    \"specificCapatibilities\":null,\n" +
              "                    \"timeout\":\"0\",\n" +
              "                    \"category\":{\n" +
              "                        \"heating\":\"0\",\n" +
              "                        \"security\":\"0\",\n" +
              "                        \"energy\":\"0\",\n" +
              "                        \"light\":\"0\",\n" +
              "                        \"automatism\":\"1\",\n" +
              "                        \"multimedia\":\"0\",\n" +
              "                        \"default\":\"0\"\n" +
              "                    },\n" +
              "                    \"display\":{\n" +
              "                        \"showObjectNameOnview\":\"1\",\n" +
              "                        \"showObjectNameOndview\":1,\n" +
              "                        \"showObjectNameOnmview\":1,\n" +
              "                        \"showOndashboard\":\"1\",\n" +
              "                        \"showOnplan\":\"1\",\n" +
              "                        \"showOnview\":\"1\",\n" +
              "                        \"showOnmobile\":\"1\",\n" +
              "                        \"showNameOndashboard\":\"1\",\n" +
              "                        \"showNameOnplan\":\"1\",\n" +
              "                        \"showNameOnview\":\"1\",\n" +
              "                        \"showNameOnmobile\":\"1\",\n" +
              "                        \"showObjectNameOndashboard\":\"0\",\n" +
              "                        \"showObjectNameOnplan\":\"0\",\n" +
              "                        \"showObjectNameOnmobile\":\"0\",\n" +
              "                        \"background-color-defaultdashboard\":\"1\",\n" +
              "                        \"background-color-transparentdashboard\":\"0\",\n" +
              "                        \"background-colordashboard\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultplan\":\"1\",\n" +
              "                        \"background-color-transparentplan\":\"0\",\n" +
              "                        \"background-colorplan\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultview\":\"1\",\n" +
              "                        \"background-color-transparentview\":\"0\",\n" +
              "                        \"background-colorview\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultmobile\":\"1\",\n" +
              "                        \"background-color-transparentmobile\":\"0\",\n" +
              "                        \"background-colormobile\":\"#19bc9c\",\n" +
              "                        \"color-defaultdashboard\":\"1\",\n" +
              "                        \"colordashboard\":\"#ffffff\",\n" +
              "                        \"color-defaultplan\":\"1\",\n" +
              "                        \"colorplan\":\"#ffffff\",\n" +
              "                        \"color-defaultview\":\"1\",\n" +
              "                        \"colorview\":\"#ffffff\",\n" +
              "                        \"color-defaultmobile\":\"1\",\n" +
              "                        \"colormobile\":\"#ffffff\",\n" +
              "                        \"border-defaultdashboard\":\"1\",\n" +
              "                        \"borderdashboard\":\"\",\n" +
              "                        \"border-defaultplan\":\"1\",\n" +
              "                        \"borderplan\":\"\",\n" +
              "                        \"border-defaultview\":\"1\",\n" +
              "                        \"borderview\":\"\",\n" +
              "                        \"border-defaultmobile\":\"1\",\n" +
              "                        \"bordermobile\":\"\",\n" +
              "                        \"border-radius-defaultdashboard\":\"1\",\n" +
              "                        \"border-radiusdashboard\":\"\",\n" +
              "                        \"border-radius-defaultplan\":\"1\",\n" +
              "                        \"border-radiusplan\":\"\",\n" +
              "                        \"border-radius-defaultview\":\"1\",\n" +
              "                        \"border-radiusview\":\"\",\n" +
              "                        \"border-radius-defaultmobile\":\"1\",\n" +
              "                        \"border-radiusmobile\":\"\",\n" +
              "                        \"parameters\":{\n" +
              "                            \"categorie\":\"SHUTTER\",\n" +
              "                            \"ondashboard\":\"true\",\n" +
              "                            \"photo\":\"\\/front\\/static\\/images\\/SHUTTER.svg\"\n" +
              "                        }\n" +
              "                    },\n" +
              "                    \"order\":\"0\",\n" +
              "                    \"cmds\":[\n" +
              "                        {\n" +
              "                            \"id\":\"867\",\n" +
              "                            \"logicalId\":\"\",\n" +
              "                            \"eqType\":\"rcswitch\",\n" +
              "                            \"name\":\"up\",\n" +
              "                            \"order\":\"0\",\n" +
              "                            \"type\":\"action\",\n" +
              "                            \"subType\":\"other\",\n" +
              "                            \"eqLogic_id\":\"127\",\n" +
              "                            \"isHistorized\":\"0\",\n" +
              "                            \"unite\":\"\",\n" +
              "                            \"configuration\":{\n" +
              "                                \"protocole\":\"homeeasy\",\n" +
              "                                \"code\":\"12325261 0 1\",\n" +
              "                                \"actionConfirm\":\"0\",\n" +
              "                                \"actionCodeAccess\":\"\",\n" +
              "                                \"actionCheckCmd\":[\n" +
              "\n" +
              "                                ]\n" +
              "                            },\n" +
              "                            \"template\":{\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"mobile\":\"\"\n" +
              "                            },\n" +
              "                            \"display\":{\n" +
              "                                \"generic_type\":\"\",\n" +
              "                                \"showOndashboard\":\"0\",\n" +
              "                                \"showOnplan\":\"0\",\n" +
              "                                \"showOnview\":\"0\",\n" +
              "                                \"showOnmobile\":\"0\",\n" +
              "                                \"showNameOndashboard\":\"0\",\n" +
              "                                \"showNameOnplan\":\"0\",\n" +
              "                                \"showNameOnview\":\"0\",\n" +
              "                                \"showNameOnmobile\":\"0\",\n" +
              "                                \"forceReturnLineBefore\":\"0\",\n" +
              "                                \"forceReturnLineAfter\":\"0\",\n" +
              "                                \"parameters\":{\n" +
              "                                    \"style\":\"button\",\n" +
              "                                    \"icon\":\"ic_arrow_upward_black_24dp\"\n" +
              "                                }\n" +
              "                            },\n" +
              "                            \"html\":{\n" +
              "                                \"enable\":\"0\",\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"dview\":\"\",\n" +
              "                                \"dplan\":\"\",\n" +
              "                                \"mobile\":\"\",\n" +
              "                                \"mview\":\"\"\n" +
              "                            },\n" +
              "                            \"value\":\"\",\n" +
              "                            \"isVisible\":\"1\"\n" +
              "                        },\n" +
              "                        {\n" +
              "                            \"id\": \"104\",\n" +
              "                            \"logicalId\": null,\n" +
              "                            \"eqType\": \"virtual\",\n" +
              "                            \"name\": \"state\",\n" +
              "                            \"order\": \"0\",\n" +
              "                            \"type\": \"info\",\n" +
              "                            \"subType\": \"binary\",\n" +
              "                            \"eqLogic_id\": \"21\",\n" +
              "                            \"isHistorized\": \"0\",\n" +
              "                            \"unite\": \"\",\n" +
              "                            \"configuration\": {\n" +
              "                                \"virtualAction\": 1,\n" +
              "                                \"calcul\": \"\",\n" +
              "                                \"returnStateValue\": \"\",\n" +
              "                                \"returnStateTime\": \"\",\n" +
              "                                \"minValue\": \"\",\n" +
              "                                \"maxValue\": \"\",\n" +
              "                                \"value\": 0\n" +
              "                            },\n" +
              "                            \"template\": null,\n" +
              "                            \"display\": {\n" +
              "                                \"invertBinary\": \"0\",\n" +
              "                                \"showOncategory\": 1,\n" +
              "                                \"showStatsOncategory\": 0,\n" +
              "                                \"showNameOncategory\": 1,\n" +
              "                                \"showOnstyle\": 1,\n" +
              "                                \"showStatsOnstyle\": 0,\n" +
              "                                \"showNameOnstyle\": 1\n" +
              "                            },\n" +
              "                            \"html\": null,\n" +
              "                            \"value\": null,\n" +
              "                            \"isVisible\": \"0\",\n" +
              "                            \"state\": 1\n" +
              "                        },\n" +
              "                        {\n" +
              "                            \"id\":\"868\",\n" +
              "                            \"logicalId\":\"\",\n" +
              "                            \"eqType\":\"rcswitch\",\n" +
              "                            \"name\":\"down\",\n" +
              "                            \"order\":\"1\",\n" +
              "                            \"type\":\"action\",\n" +
              "                            \"subType\":\"other\",\n" +
              "                            \"eqLogic_id\":\"127\",\n" +
              "                            \"isHistorized\":\"0\",\n" +
              "                            \"unite\":\"\",\n" +
              "                            \"configuration\":{\n" +
              "                                \"protocole\":\"homeeasy\",\n" +
              "                                \"code\":\"12325261 0 0\",\n" +
              "                                \"actionConfirm\":\"0\",\n" +
              "                                \"actionCodeAccess\":\"\",\n" +
              "                                \"actionCheckCmd\":[\n" +
              "\n" +
              "                                ]\n" +
              "                            },\n" +
              "                            \"template\":{\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"mobile\":\"\"\n" +
              "                            },\n" +
              "                            \"display\":{\n" +
              "                                \"generic_type\":\"\",\n" +
              "                                \"showOndashboard\":\"0\",\n" +
              "                                \"showOnplan\":\"0\",\n" +
              "                                \"showOnview\":\"0\",\n" +
              "                                \"showOnmobile\":\"0\",\n" +
              "                                \"showNameOndashboard\":\"0\",\n" +
              "                                \"showNameOnplan\":\"0\",\n" +
              "                                \"showNameOnview\":\"0\",\n" +
              "                                \"showNameOnmobile\":\"0\",\n" +
              "                                \"forceReturnLineBefore\":\"0\",\n" +
              "                                \"forceReturnLineAfter\":\"0\",\n" +
              "                                \"parameters\":{\n" +
              "                                    \"style\":\"button\",\n" +
              "                                    \"icon\":\"ic_arrow_downward_black_24dp\"\n" +
              "                                }\n" +
              "                            },\n" +
              "                            \"html\":{\n" +
              "                                \"enable\":\"0\",\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"dview\":\"\",\n" +
              "                                \"dplan\":\"\",\n" +
              "                                \"mobile\":\"\",\n" +
              "                                \"mview\":\"\"\n" +
              "                            },\n" +
              "                            \"value\":\"\",\n" +
              "                            \"isVisible\":\"1\"\n" +
              "                        }\n" +
              "                    ]\n" +
              "                }\n" +
              "            ]\n" +
              "        },\n" +
              "        {\n" +
              "            \"id\":\"14\",\n" +
              "            \"name\":\"Chambre d'amis\",\n" +
              "            \"father_id\":\"9\",\n" +
              "            \"isVisible\":\"1\",\n" +
              "            \"position\":null,\n" +
              "            \"configuration\":null,\n" +
              "            \"display\":{\n" +
              "                \"icon\":\"\",\n" +
              "                \"tagColor\":\"#9b59b6\",\n" +
              "                \"tagTextColor\":\"#ffffff\"\n" +
              "            },\n" +
              "            \"eqLogics\":[\n" +
              "                {\n" +
              "                    \"id\":\"138\",\n" +
              "                    \"name\":\"Lampe chambre amis\",\n" +
              "                    \"logicalId\":\"\",\n" +
              "                    \"object_id\":\"14\",\n" +
              "                    \"eqType_name\":\"rcswitch\",\n" +
              "                    \"eqReal_id\":null,\n" +
              "                    \"isVisible\":\"1\",\n" +
              "                    \"isEnable\":\"1\",\n" +
              "                    \"configuration\":{\n" +
              "                        \"createtime\":\"2016-05-22 19:44:30\",\n" +
              "                        \"updatetime\":\"2016-05-25 22:11:08\",\n" +
              "                        \"battery_type\":\"\",\n" +
              "                        \"batteryStatusDatetime\":\"\",\n" +
              "                        \"batteryStatus\":\"\",\n" +
              "                        \"battery_danger_threshold\":\"\",\n" +
              "                        \"battery_warning_threshold\":\"\"\n" +
              "                    },\n" +
              "                    \"specificCapatibilities\":null,\n" +
              "                    \"timeout\":\"0\",\n" +
              "                    \"category\":{\n" +
              "                        \"heating\":\"0\",\n" +
              "                        \"security\":\"0\",\n" +
              "                        \"energy\":\"0\",\n" +
              "                        \"light\":\"1\",\n" +
              "                        \"automatism\":\"0\",\n" +
              "                        \"multimedia\":\"0\",\n" +
              "                        \"default\":\"0\"\n" +
              "                    },\n" +
              "                    \"display\":{\n" +
              "                        \"showObjectNameOnview\":\"1\",\n" +
              "                        \"showObjectNameOndview\":1,\n" +
              "                        \"showObjectNameOnmview\":1,\n" +
              "                        \"showOndashboard\":\"1\",\n" +
              "                        \"showOnplan\":\"1\",\n" +
              "                        \"showOnview\":\"1\",\n" +
              "                        \"showOnmobile\":\"1\",\n" +
              "                        \"showNameOndashboard\":\"1\",\n" +
              "                        \"showNameOnplan\":\"1\",\n" +
              "                        \"showNameOnview\":\"1\",\n" +
              "                        \"showNameOnmobile\":\"1\",\n" +
              "                        \"showObjectNameOndashboard\":\"0\",\n" +
              "                        \"showObjectNameOnplan\":\"0\",\n" +
              "                        \"showObjectNameOnmobile\":\"0\",\n" +
              "                        \"background-color-defaultdashboard\":\"1\",\n" +
              "                        \"background-color-transparentdashboard\":\"0\",\n" +
              "                        \"background-colordashboard\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultplan\":\"1\",\n" +
              "                        \"background-color-transparentplan\":\"0\",\n" +
              "                        \"background-colorplan\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultview\":\"1\",\n" +
              "                        \"background-color-transparentview\":\"0\",\n" +
              "                        \"background-colorview\":\"#19bc9c\",\n" +
              "                        \"background-color-defaultmobile\":\"1\",\n" +
              "                        \"background-color-transparentmobile\":\"0\",\n" +
              "                        \"background-colormobile\":\"#19bc9c\",\n" +
              "                        \"color-defaultdashboard\":\"1\",\n" +
              "                        \"colordashboard\":\"#ffffff\",\n" +
              "                        \"color-defaultplan\":\"1\",\n" +
              "                        \"colorplan\":\"#ffffff\",\n" +
              "                        \"color-defaultview\":\"1\",\n" +
              "                        \"colorview\":\"#ffffff\",\n" +
              "                        \"color-defaultmobile\":\"1\",\n" +
              "                        \"colormobile\":\"#ffffff\",\n" +
              "                        \"border-defaultdashboard\":\"1\",\n" +
              "                        \"borderdashboard\":\"\",\n" +
              "                        \"border-defaultplan\":\"1\",\n" +
              "                        \"borderplan\":\"\",\n" +
              "                        \"border-defaultview\":\"1\",\n" +
              "                        \"borderview\":\"\",\n" +
              "                        \"border-defaultmobile\":\"1\",\n" +
              "                        \"bordermobile\":\"\",\n" +
              "                        \"border-radius-defaultdashboard\":\"1\",\n" +
              "                        \"border-radiusdashboard\":\"\",\n" +
              "                        \"border-radius-defaultplan\":\"1\",\n" +
              "                        \"border-radiusplan\":\"\",\n" +
              "                        \"border-radius-defaultview\":\"1\",\n" +
              "                        \"border-radiusview\":\"\",\n" +
              "                        \"border-radius-defaultmobile\":\"1\",\n" +
              "                        \"border-radiusmobile\":\"\",\n" +
              "                        \"parameters\":{\n" +
              "                            \"categorie\":\"SOCKET\",\n" +
              "                            \"photo\":\"\\/front\\/static\\/images\\/ampoule.svg\"\n" +
              "                        }\n" +
              "                    },\n" +
              "                    \"order\":\"0\",\n" +
              "                    \"cmds\":[\n" +
              "                        {\n" +
              "                            \"id\":\"889\",\n" +
              "                            \"logicalId\":\"\",\n" +
              "                            \"eqType\":\"rcswitch\",\n" +
              "                            \"name\":\"on\",\n" +
              "                            \"order\":\"0\",\n" +
              "                            \"type\":\"action\",\n" +
              "                            \"subType\":\"other\",\n" +
              "                            \"eqLogic_id\":\"138\",\n" +
              "                            \"isHistorized\":\"0\",\n" +
              "                            \"unite\":\"\",\n" +
              "                            \"configuration\":{\n" +
              "                                \"protocole\":\"homeeasy\",\n" +
              "                                \"code\":\"12325261 2 1\",\n" +
              "                                \"actionConfirm\":\"0\",\n" +
              "                                \"actionCodeAccess\":\"\",\n" +
              "                                \"actionCheckCmd\":[\n" +
              "\n" +
              "                                ]\n" +
              "                            },\n" +
              "                            \"template\":{\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"mobile\":\"\"\n" +
              "                            },\n" +
              "                            \"display\":{\n" +
              "                                \"generic_type\":\"\",\n" +
              "                                \"showOndashboard\":\"0\",\n" +
              "                                \"showOnplan\":\"0\",\n" +
              "                                \"showOnview\":\"0\",\n" +
              "                                \"showOnmobile\":\"0\",\n" +
              "                                \"showNameOndashboard\":\"0\",\n" +
              "                                \"showNameOnplan\":\"0\",\n" +
              "                                \"showNameOnview\":\"0\",\n" +
              "                                \"showNameOnmobile\":\"0\",\n" +
              "                                \"forceReturnLineBefore\":\"0\",\n" +
              "                                \"forceReturnLineAfter\":\"0\",\n" +
              "                                \"parameters\":{\n" +
              "                                    \"style\":\"button\",\n" +
              "                                    \"icon\":\"ic_power_settings_new_black_24dp\"\n" +
              "                                }\n" +
              "                            },\n" +
              "                            \"html\":{\n" +
              "                                \"enable\":\"0\",\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"dview\":\"\",\n" +
              "                                \"dplan\":\"\",\n" +
              "                                \"mobile\":\"\",\n" +
              "                                \"mview\":\"\"\n" +
              "                            },\n" +
              "                            \"value\":\"\",\n" +
              "                            \"isVisible\":\"1\"\n" +
              "                        },\n" +
              "                        {\n" +
              "                            \"id\": \"104\",\n" +
              "                            \"logicalId\": null,\n" +
              "                            \"eqType\": \"virtual\",\n" +
              "                            \"name\": \"state\",\n" +
              "                            \"order\": \"0\",\n" +
              "                            \"type\": \"info\",\n" +
              "                            \"subType\": \"binary\",\n" +
              "                            \"eqLogic_id\": \"21\",\n" +
              "                            \"isHistorized\": \"0\",\n" +
              "                            \"unite\": \"\",\n" +
              "                            \"configuration\": {\n" +
              "                                \"virtualAction\": 1,\n" +
              "                                \"calcul\": \"\",\n" +
              "                                \"returnStateValue\": \"\",\n" +
              "                                \"returnStateTime\": \"\",\n" +
              "                                \"minValue\": \"\",\n" +
              "                                \"maxValue\": \"\",\n" +
              "                                \"value\": 0\n" +
              "                            },\n" +
              "                            \"template\": null,\n" +
              "                            \"display\": {\n" +
              "                                \"invertBinary\": \"0\",\n" +
              "                                \"showOncategory\": 1,\n" +
              "                                \"showStatsOncategory\": 0,\n" +
              "                                \"showNameOncategory\": 1,\n" +
              "                                \"showOnstyle\": 1,\n" +
              "                                \"showStatsOnstyle\": 0,\n" +
              "                                \"showNameOnstyle\": 1\n" +
              "                            },\n" +
              "                            \"html\": null,\n" +
              "                            \"value\": null,\n" +
              "                            \"isVisible\": \"0\",\n" +
              "                            \"state\": 0\n" +
              "                        },\n" +
              "                        {\n" +
              "                            \"id\":\"890\",\n" +
              "                            \"logicalId\":\"\",\n" +
              "                            \"eqType\":\"rcswitch\",\n" +
              "                            \"name\":\"off\",\n" +
              "                            \"order\":\"1\",\n" +
              "                            \"type\":\"action\",\n" +
              "                            \"subType\":\"other\",\n" +
              "                            \"eqLogic_id\":\"138\",\n" +
              "                            \"isHistorized\":\"0\",\n" +
              "                            \"unite\":\"\",\n" +
              "                            \"configuration\":{\n" +
              "                                \"protocole\":\"homeeasy\",\n" +
              "                                \"code\":\"12325261 2 0\",\n" +
              "                                \"actionConfirm\":\"0\",\n" +
              "                                \"actionCodeAccess\":\"\",\n" +
              "                                \"actionCheckCmd\":[\n" +
              "\n" +
              "                                ]\n" +
              "                            },\n" +
              "                            \"template\":{\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"mobile\":\"\"\n" +
              "                            },\n" +
              "                            \"display\":{\n" +
              "                                \"generic_type\":\"\",\n" +
              "                                \"showOndashboard\":\"0\",\n" +
              "                                \"showOnplan\":\"0\",\n" +
              "                                \"showOnview\":\"0\",\n" +
              "                                \"showOnmobile\":\"0\",\n" +
              "                                \"showNameOndashboard\":\"0\",\n" +
              "                                \"showNameOnplan\":\"0\",\n" +
              "                                \"showNameOnview\":\"0\",\n" +
              "                                \"showNameOnmobile\":\"0\",\n" +
              "                                \"forceReturnLineBefore\":\"0\",\n" +
              "                                \"forceReturnLineAfter\":\"0\",\n" +
              "                                \"parameters\":{\n" +
              "                                    \"style\":\"button\",\n" +
              "                                    \"icon\":\"ic_highlight_off_black_24dp\"\n" +
              "                                }\n" +
              "                            },\n" +
              "                            \"html\":{\n" +
              "                                \"enable\":\"0\",\n" +
              "                                \"dashboard\":\"\",\n" +
              "                                \"dview\":\"\",\n" +
              "                                \"dplan\":\"\",\n" +
              "                                \"mobile\":\"\",\n" +
              "                                \"mview\":\"\"\n" +
              "                            },\n" +
              "                            \"value\":\"\",\n" +
              "                            \"isVisible\":\"1\"\n" +
              "                        }\n" +
              "                    ]\n" +
              "                }\n" +
              "            ]\n" +
              "        },\n" +
              "        {\n" +
              "            \"id\":\"16\",\n" +
              "            \"name\":\"Toilettes haut\",\n" +
              "            \"father_id\":\"9\",\n" +
              "            \"isVisible\":\"1\",\n" +
              "            \"position\":null,\n" +
              "            \"configuration\":null,\n" +
              "            \"display\":{\n" +
              "                \"icon\":\"\",\n" +
              "                \"tagColor\":\"#9b59b6\",\n" +
              "                \"tagTextColor\":\"#ffffff\"\n" +
              "            },\n" +
              "            \"eqLogics\":[\n" +
              "\n" +
              "            ]\n" +
              "        }\n" +
              "    ]\n" +
              "}\n";

        JSONObject js = null;
        try {
            js = new JSONObject(json);
        } catch (final JSONException e) {
            Log.e(TAG, "Json convertion error: " + e.getMessage());
        }

      return js;
    }

    private String gatewayRequestCmd(String url) {
// Tag used to cancel the request
        final ProgressDialog pDialog = new ProgressDialog(mCtx);
        pDialog.setMessage("Sending...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response");
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });
        HttpHandler.getInstance(mCtx).addToRequestQueue(jsonObjReq);
        return "";

    }


    private String gatewayRequest(String url) {
// Tag used to cancel the request
        final ProgressDialog pDialog = new ProgressDialog(mCtx);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        manageResponse(response);
                        //manageResponse(getDevicesMoke());

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });
        HttpHandler.getInstance(mCtx).addToRequestQueue(jsonObjReq);
        return "";

    }

    public void refresh() {
        initCategories();
        String ret = gatewayRequest(getUrl(JEEDOM_API_GETDEVICES));
    }

     private void manageResponse(JSONObject jsonObj) {
        try {
            JSONArray objets = jsonObj.getJSONArray("result");

            //Iterate on Location
            for (int i = 0; i < objets.length(); i++) {
                JSONObject location = objets.getJSONObject(i);
                boolean isVisible = location.getString("isVisible").equals("1");
                if (isVisible) {

                    //On location
                    String id = location.getString("id");
                    String name = location.getString("name");
                    Location l = new Location(id,name);
                    Log.v(TAG, "Location = " + l);
                    mAuto.getLocations().put(id,l);
                    setDevices(location,l);

                }
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    private void setDevices(JSONObject location, Location loc) {
        try {
            JSONArray devicesJsonStr = location.getJSONArray("eqLogics");

            //Iterate on device
            for (int i = 0; i < devicesJsonStr.length(); i++) {
                JSONObject deviceJsonStr = devicesJsonStr.getJSONObject(i);

                //Get Category
                JSONObject cat = deviceJsonStr.getJSONObject("category");
                Log.v(TAG,"get cat");
                Iterator keys = cat.keys();

                //Iterate on device
                Device dev = addDevice(deviceJsonStr);

                if (dev!=null) {
                    dev.setLocation(loc);
                    loc.addDevice();
                    loc.setVisible(true);
                    while (keys.hasNext()) {
                        String currentDynamicKey = (String) keys.next();
                        Log.v(TAG, "Key = " + currentDynamicKey);

                        // get the value of the dynamic key
                        String currentDynamicValue = cat.getString(currentDynamicKey);
                        Log.v(TAG, "Value = " + currentDynamicValue);

                        //Is Active ?
                        if (currentDynamicValue.equals("1")) {
                            //Activate category
                            Log.v(TAG, "Activate category :" + currentDynamicKey);
                            mAuto.getCategories().get(currentDynamicKey).setVisible(true);
                            mAuto.getCategories().get(currentDynamicKey).addDevice();
                            dev.addCategory(currentDynamicKey);
                        }
                        Log.v(TAG, "Device found :" + deviceJsonStr.getString("name"));
                        // do something here with the value...
                    }
                    Log.v(TAG, "NEW DEVICE");
                    Log.v(TAG, dev.toString());
                    mAuto.getDevices().add(dev);
                }
            }
        }
        catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    private static Device addDevice(JSONObject deviceJsonStr) {


        Device device = null;
        String categorie=null;
        try {
            if (deviceJsonStr.has("display") && deviceJsonStr.getJSONObject("display").has("parameters") && deviceJsonStr.getJSONObject("display").getJSONObject("parameters").has("categorie")) {
                categorie = deviceJsonStr.getJSONObject("display").getJSONObject("parameters").getString("categorie");
            }
            device = new Device(deviceJsonStr.getString("id"),deviceJsonStr.getString("name"),categorie);

            if (deviceJsonStr.has("display") && deviceJsonStr.getJSONObject("display").has("parameters")) {
                if (deviceJsonStr.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.icon.off") )
                    device.setIconOff(deviceJsonStr.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.icon.off"));
                if (deviceJsonStr.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.icon.on") )
                    device.setIconOn(deviceJsonStr.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.icon.on"));
            }


            JSONArray ctrls = deviceJsonStr.getJSONArray("cmds");

            //Iterate on device
            for (int i = 0; i < ctrls.length(); i++) {
                JSONObject cmd = ctrls.getJSONObject(i);

                Log.v(TAG,"Control found = " + cmd.getString("name") + "/"+cmd.getString("isVisible")+"/"+cmd.getString("type"));
                //is Visible
                //info
                if (cmd.getString("isVisible").equals("1") && cmd.getString("type").equals("info")) {
                    Info info = new Info(cmd.getString("id"),cmd.getString("name"));
                    info.setValue(cmd.getString("state"));
                    if (cmd.has("display") && cmd.getJSONObject("display").has("parameters")) {
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.favorite.order"))
                            info.setFavoriteOrder(cmd.getJSONObject("display").getJSONObject("parameters").getInt("jarvis4mobile.favorite.order"));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.type"))
                            info.setType(cmd.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.type"));
                    }
                    Log.v(TAG,"Add info " + cmd.getString("name"));
                    device.getInfos().add(info);
                }

                //action/controls
                if (cmd.getString("isVisible").equals("1") && cmd.getString("type").equals("action")) {
                    Control ctrl = new Control(cmd.getString("id"), cmd.getString("name"));
                    if (cmd.getJSONObject("display").has("forceReturnLineAfter"))
                        ctrl.setForceReturnLineAfter(cmd.getJSONObject("display").getString("forceReturnLineAfter").equals("1"));
                    if (cmd.has("display") && cmd.getJSONObject("display").has("parameters")) {
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.icon"))
                           ctrl.setIcon(cmd.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.icon"));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.style"))
                           ctrl.setStyle(cmd.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.style"));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("ondashboard"))
                            ctrl.setOnDashboard(new Boolean(cmd.getJSONObject("display").getJSONObject("parameters").getString("ondashboard")));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("defaultValue"))
                            ctrl.setDefaultValue(cmd.getJSONObject("display").getJSONObject("parameters").getString("defaultValue"));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("step"))
                            ctrl.setStep(cmd.getJSONObject("display").getJSONObject("parameters").getString("step"));
                        if (cmd.getJSONObject("display").getJSONObject("parameters").has("jarvis4mobile.type"))
                            ctrl.setType(cmd.getJSONObject("display").getJSONObject("parameters").getString("jarvis4mobile.type"));
                    }
                    if (cmd.has("configuration")) {
                        if (cmd.getJSONObject("configuration").has("maxValue"))
                            ctrl.setMaxValue(cmd.getJSONObject("configuration").getString("maxValue"));
                        if (cmd.getJSONObject("configuration").has("minValue"))
                            ctrl.setMinValue(cmd.getJSONObject("configuration").getString("minValue"));
                    }
                    Log.v(TAG,"Add control " + cmd.getString("name"));
                    device.getControls().add(ctrl);
                }

            }
        }
        catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return device;

    }

    private void initCategories() {
        String[] jeedomCat = { "heating","security","energy","light","automatism","multimedia","default"};
        String[] libCat = { "Audio","Securité","Energie","Lumière","Automatisme","Media","Defaut"};
        String[] foreColor= { "#7401DF","#2196f3","#2196f3","#DF0101","#088A85","#fe9c17","#2196f3"};

        for (int i = 0; i < jeedomCat.length;  i++) {
            mAuto.getCategories().put(jeedomCat[i], new Category(jeedomCat[i], libCat[i],foreColor[i]));
        }
    }

}