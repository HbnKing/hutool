package cn.hutool.core.stream;

import cn.hutool.core.collection.CollUtil;

import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Stream} 工具类
 *
 * @author looly
 * @since 5.6.7
 */
public class StreamUtil {

	@SafeVarargs
	public static <T> Stream<T> of(T... values) {
		if (null == values) {
			return null;
		}
		return Stream.of(values);
	}

	/**
	 * {@link Iterable}转换为{@link Stream}，默认非并行
	 *
	 * @param iterable 集合
	 * @param <T>      集合元素类型
	 * @return {@link Stream}
	 */
	public static <T> Stream<T> of(Iterable<T> iterable) {
		return of(iterable, false);
	}

	/**
	 * {@link Iterable}转换为{@link Stream}
	 *
	 * @param iterable 集合
	 * @param parallel 是否并行
	 * @param <T>      集合元素类型
	 * @return {@link Stream}
	 */
	public static <T> Stream<T> of(Iterable<T> iterable, boolean parallel) {
		if (null == iterable) {
			return null;
		}
		return StreamSupport.stream(
				Spliterators.spliterator(CollUtil.toCollection(iterable), 0),
				parallel);
	}

	/**
	 * 通过函数创建Stream
	 *
	 * @param seed           初始值
	 * @param elementCreator 递进函数，每次调用此函数获取下一个值
	 * @param limit          限制个数
	 * @param <T>            创建元素类型
	 * @return {@link Stream}
	 */
	public static <T> Stream<T> of(T seed, UnaryOperator<T> elementCreator, int limit) {
		return Stream.iterate(seed, elementCreator).limit(limit);
	}

	/**
	 * 将Stream中所有元素以指定分隔符，合并为一个字符串，对象默认调用toString方法
	 *
	 * @param stream       {@link Stream}
	 * @param delimiter    分隔符
	 * @param <T>          元素类型
	 * @return 字符串
	 */
	public static <T> String join(Stream<T> stream, CharSequence delimiter) {
		return stream.collect(CollectorUtil.joining(delimiter));
	}

	/**
	 * 将Stream中所有元素以指定分隔符，合并为一个字符串
	 *
	 * @param stream       {@link Stream}
	 * @param delimiter    分隔符
	 * @param toStringFunc 元素转换为字符串的函数
	 * @param <T>          元素类型
	 * @return 字符串
	 */
	public static <T> String join(Stream<T> stream, CharSequence delimiter,
								  Function<T, ? extends CharSequence> toStringFunc) {
		return stream.collect(CollectorUtil.joining(delimiter, toStringFunc));
	}
}
