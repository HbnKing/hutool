/*
 * Copyright (c) 2023 looly(loolly@aliyun.com)
 * Hutool is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          https://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package org.dromara.hutool.json;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JSONSupportTest {

	/**
	 * https://github.com/dromara/hutool/issues/1779
	 * 在JSONSupport的JSONBeanParse中，如果使用json.toBean，会导致JSONBeanParse.parse方法反复递归调用，最终栈溢出<br>
	 * 因此parse方法默认实现必须避开JSONBeanParse.parse调用。
	 */
	@Test
	public void parseTest() {
		final String jsonstr = "{\n" +
				"    \"location\": \"https://hutool.cn\",\n" +
				"    \"message\": \"这是一条测试消息\",\n" +
				"    \"requestId\": \"123456789\",\n" +
				"    \"traceId\": \"987654321\"\n" +
				"}";


		final TestBean testBean = JSONUtil.toBean(jsonstr, TestBean.class);
		Assertions.assertEquals("https://hutool.cn", testBean.getLocation());
		Assertions.assertEquals("这是一条测试消息", testBean.getMessage());
		Assertions.assertEquals("123456789", testBean.getRequestId());
		Assertions.assertEquals("987654321", testBean.getTraceId());
	}

	@EqualsAndHashCode(callSuper = true)
	@Data
	static class TestBean  extends JSONSupport{

		private String location;

		private String message;

		private String requestId;

		private String traceId;

	}
}