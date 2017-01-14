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
    private static String JEEDOM_API_CMDREQUESTPATTERN = "request={\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"cmd::execCmd\",\"params\":{\" + apikey\":\"@APIKEY\",\"id\": '@ID' }}";
    private static String JEEDOM_API_SLICERREQUESTPATTERN = "request={\"jsonrpc\":\"2.0\",\"method\":\"cmd::execCmd\",\"params\":{\"apikey\":\"@APIKEY\",\"id\": \"@ID\", \"options\":{\"slider\": \"@SLIDER\"}}}";
    private static String JEEDOM_API_GETDEVICES = "request={\"jsonrpc\":\"2.0\",\"method\":\"object::fullEq\",\"params\":{\"apikey\":\"@APIKEY\"}}";



    public void sendCmd(String cmdId) {
        String url = getUrl(JEEDOM_API_CMDREQUESTPATTERN,cmdId);
        Toast toast = Toast.makeText(mCtx, url, Toast.LENGTH_SHORT );
        toast.show();
    }

    public void sendSlicerCmd(String cmdId) {
        String url = getUrl(JEEDOM_API_SLICERREQUESTPATTERN,cmdId);
        Toast toast = Toast.makeText(mCtx, url, Toast.LENGTH_SHORT );
        toast.show();
    }

    private String getUrl(String pattern) {
        String url = JEEDOM_API_PREFIX + pattern.replace("@APIKEY",JEEDOM_API_KEY);
        return url;
    }

    private String getUrl(String pattern, String cmdId) {
        String url = JEEDOM_API_PREFIX + pattern.replace("@ID",cmdId);
        url = url.replace("@APIKEY",JEEDOM_API_KEY);
        url = url.replace("@SLIDER",JEEDOM_API_KEY);
        return url;
    }

    private String getDevicesMoke() {
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
              "                                    \"icon\":\"icons:power-settings-new\"\n" +
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
              "                                    \"icon\":\"icons:highlight-off\"\n" +
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

      return json;
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
                        Log.d(TAG, response.toString());
                        manageResponse(response);

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
                    mAuto.getLocations().add(l);
                    setDevices(location);

                }
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    private void setDevices(JSONObject location) {
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
                while(keys.hasNext()) {
                    String currentDynamicKey = (String)keys.next();
                    Log.v(TAG,"Key = " + currentDynamicKey);

                    // get the value of the dynamic key
                    String currentDynamicValue = cat.getString(currentDynamicKey);
                    Log.v(TAG,"Value = " + currentDynamicValue);

                    //Is Active ?
                    if (currentDynamicValue.equals("1")) {
                        //Activate category
                        mAuto.getCategories().get(currentDynamicKey).setVisible(true);
                    }
                    Log.v(TAG,"Device found :" + deviceJsonStr.getString("name"));
                    Device dev = new Device(deviceJsonStr.getString("id"),deviceJsonStr.getString("name"),deviceJsonStr.getJSONObject("display").getJSONObject("parameters").getString("categorie"));

                    setControls(dev, deviceJsonStr);


                    // do something here with the value...
                }
            }
        }
        catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    private static void setControls(Device dev, JSONObject device) {
        try {
            JSONArray ctrls = device.getJSONArray("cmds");

            //Iterate on device
            for (int i = 0; i < ctrls.length(); i++) {
                JSONObject cmd = ctrls.getJSONObject(i);

                Log.v(TAG,"Control found = " + cmd.getString("name"));
                //is Visible
                if (cmd.getString("isVisible").equals("1") && cmd.getString("type").equals("action")) {
                    Control ctrl = new Control(cmd.getString("id"),cmd.getString("name"));
                    ctrl.setIcon(cmd.getJSONObject("display").getJSONObject("parameters").getString("icon"));
                    ctrl.setStyle(cmd.getJSONObject("display").getJSONObject("parameters").getString("style"));

                    dev.addControl(ctrl);
                }

            }
        }
        catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    private void initCategories() {
        String[] jeedomCat = { "heating","security","energy","light","automatism","multimedia","default"};
        String[] libCat = { "Chauffage","Securité","Ennergie","Lumière","Automatisme","Media","Defaut"};
        String[] foreColor= { "#2196f3","#2196f3","#2196f3","#2196f3","#009688","#2196f3","#2196f3"};

        for (int i = 0; i < jeedomCat.length;  i++) {
            mAuto.getCategories().put(jeedomCat[i], new Category(jeedomCat[i], libCat[i],foreColor[i]));
        }
    }

}