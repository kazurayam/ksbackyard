package com.kazurayam.ksbackyard

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.TestObjectProperty

class TestObjectSupport {

	/**
	 * convert a TestObject object into a String in JSON format
	 * while filtering its active properties only
	 * 
	 * @param testObject
	 * @return e.g.
	 * <pre>
	 [
	 {"name":"class","condition":"equals","value":"android.widget.TextView","isActive":true},
	 {"name":"instance","condition":"equals","value":"3","isActive":true},
	 {"name":"text","condition":"equals","value":"App","isActive":true},
	 {"name":"resource-id","condition":"equals","value":"android:id/text1","isActive":true}
	 ]
	 </pre>
	 */
	@Keyword
	static String jsonifyActiveProperties(TestObject testObject) {
		List<TestObjectProperty> lst = testObject.getActiveProperties()
		StringBuilder sb = new StringBuilder()
		sb.append('[\n')
		int count = 0
		for (TestObjectProperty top : lst) {
			if (count > 0) {
				sb.append(',')
				sb.append('\n')
			}
			sb.append('{')
			sb.append('\"name\":')
			sb.append("\"${top.getName()}\"")
			sb.append(',')
			sb.append('\"condition\":')
			sb.append("\"${top.getCondition().toString()}\"")
			sb.append(',')
			sb.append('\"value\":')
			sb.append("\"${top.getValue()}\"")
			sb.append(',')
			sb.append('\"isActive\":')
			sb.append("${top.isActive()}")
			sb.append('}')
			count += 1
		}
		sb.append('\n]')
		return sb.toString()
	}
}