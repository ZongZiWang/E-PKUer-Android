package pku.cs.epkuer.util;

import org.json.JSONArray;

public class Restaurant {
	public int id = 0;
	public String name = null;
	public int busy = 0;
	public JSONArray recommendations = null;
	public String image_url = null;
	public float evaluation = 0;
	public String location_name = null;
	public String location_zone = null;
	public double location_latitude = 0;
	public double location_longitude = 0;
	public float average_cost = 0;
	public String dishes = null;
	public Comment rct_cmt1, rct_cmt2;
}
