/*
 * Project Name: kmfex-platform
 * File Name: ConverterService.java
 * Class Name: ConverterService
 *
 * Copyright 2014 KMFEX Inc
 *
 * 
 *
 * http://www.kmfex.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fushun.framework.util.beans;

import com.fushun.framework.base.BaseCMP;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.bean.properties.config.IBeanCopyPropertiesBean;
import com.fushun.framework.util.exception.ConverterException;
import com.fushun.framework.util.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 对象转换类
 * @author wenzc
 */
@SuppressWarnings("rawtypes")
public final class ConverterUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConverterUtil.class);
    private static final Map<String, BeanCopier> CACHED_COPIER_MAP = new ConcurrentHashMap<String, BeanCopier>();
    private static final Map<String, ObjectConverter> CACHED_CUSTOM_CONVERTER_MAP = new ConcurrentHashMap<String, ObjectConverter>();
    private static final Map<String, EInvokeMethod> CACHED_INVOKE_METHOD = new HashMap<String, EInvokeMethod>();

    /**
     * sourceList集合中所有的数据赋值到List&lttarget&gt集合类型
     *
     * @param source     源数据泛型类型
     * @param target     目标数据泛型类型
     * @param sourceList 源数据List集合
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertList(Class<T> source, Class<F> target, List<T> sourceList) {
        return convertList(source, target, sourceList, null, null);
    }

    /**
     * sourceList集合中所有的数据赋值到List&lttarget&gt集合类型
     * <br/>再通过customConverterClass赋值数据
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceList           源数据List集合
     * @param converter            传入null
     * @param customConverterClass 自定义实现转换类
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertList(Class<T> source, Class<F> target, List<T> sourceList, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        List<F> targetList = new ArrayList();
        if (sourceList != null && sourceList.size() != 0) {

            for (T t : sourceList) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, converter, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * 通过customConverterClass实现赋值sourceList集合中的数据到List&lttarget&gt集合类型
     * <br/><font color='red'>不拷贝源数据Class中的所有字段</font>
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceList           源数据List集合
     * @param customConverterClass 自定义实现转换类
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertList(Class<T> source, Class<F> target, List<T> sourceList, Class<? extends ObjectConverter> customConverterClass) {
        List<F> targetList = new ArrayList();
        if (sourceList != null && sourceList.size() != 0) {

            for (T t : sourceList) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * sourceSet集合中所有的数据赋值到Set&lttarget&gt集合类型
     *
     * @param source    源数据泛型类型
     * @param target    目标数据泛型类型
     * @param sourceSet 源数据Set集合
     * @return 返回Set&lttarget&gt集合类型
     * @author fushun
     * @version
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> Set<F> convertSet(Class<T> source, Class<F> target, Set<T> sourceSet) {
        return convertSet(source, target, sourceSet, null, null);
    }

    /**
     * sourceSet集合中所有的数据赋值到Set&lttarget&gt集合类型
     * <br/>再通过customConverterClass赋值数据
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceSet            源数据Set集合
     * @param converter            传入null
     * @param customConverterClass 自定义实现转换类
     * @return 返回Set&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> Set<F> convertSet(Class<T> source, Class<F> target, Set<T> sourceSet, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        Set<F> targetList = new HashSet();
        if (sourceSet != null && sourceSet.size() != 0) {

            for (T t : sourceSet) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, converter, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * 通过customConverterClass实现赋值sourceList集合中的数据到Set&lttarget&gt集合类型
     * <br/><font color='red'>不拷贝源数据Class中的所有字段</font>
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceSet            源数据Set集合
     * @param customConverterClass 自定义实现转换类
     * @return 返回Set&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> Set<F> convertSet(Class<T> source, Class<F> target, Set<T> sourceSet, Class<? extends ObjectConverter> customConverterClass) {
        Set<F> targetList = new HashSet<F>();
        if (sourceSet != null && sourceSet.size() != 0) {

            for (T t : sourceSet) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * sourceIterable集合中所有的数据赋值到List&lttarget&gt集合类型
     *
     * @param source         源数据泛型类型
     * @param target         目标数据泛型类型
     * @param sourceIterable 源数据Iterable集合
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertIterableToList(Class<T> source, Class<F> target, Iterable<T> sourceIterable) {
        return convertIterableToList(source, target, sourceIterable, null, null);
    }

    /**
     * sourceIterable集合中所有的数据赋值到List&lttarget&gt集合类型
     * <br/>再通过customConverterClass赋值数据
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceIterable       源数据Iterable集合
     * @param converter            传入null
     * @param customConverterClass 自定义实现转换类
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertIterableToList(Class<T> source, Class<F> target, Iterable<T> sourceIterable, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        List<F> targetList = new ArrayList();
        if (sourceIterable != null) {

            for (T t : sourceIterable) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, converter, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * 通过customConverterClass实现赋值sourceIterable集合中的数据到Iterable&lttarget&gt集合类型
     * <br/><font color='red'>不拷贝源数据Class中的所有字段</font>
     *
     * @param source               源数据泛型类型
     * @param target               目标数据泛型类型
     * @param sourceIterable       源数据Iterable集合
     * @param customConverterClass 自定义实现转换类
     * @return 返回List&lttarget&gt集合类型
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> List<F> convertIterableToList(Class<T> source, Class<F> target, Iterable<T> sourceIterable, Class<? extends ObjectConverter> customConverterClass) {
        List<F> targetList = new ArrayList();
        if (sourceIterable != null) {

            for (T t : sourceIterable) {
                try {
                    F f = target.newInstance();
                    targetList.add(convert(t, f, customConverterClass));
                } catch (Throwable e) {
                    logger.error("When copy instance" + t, e);
                    throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                }
            }
            return targetList;
        } else {
            return targetList;
        }

    }

    /**
     * 拷贝source中所有数据到target
     *
     * @param source 源数据
     * @param target 目标数据
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> F convert(T source, F target) {
        return convert(source, target, null, null);
    }

    /**
     * 通过customConverterClass实现赋值source数据到target中
     * <br/><font color='red'>不拷贝source中的所有字段</font>
     *
     * @param source               源数据
     * @param target               目标数据
     * @param customConverterClass 自定义实现转换类
     * @return
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> F convert(T source, F target, Class<? extends ObjectConverter> customConverterClass) {
        if (source == null || target == null) {
            return null;
        }
        if (customConverterClass == null) {
            return null;
        }
        copy(source, target, customConverterClass);
        return target;
    }

    /**
     * 拷贝source中所有数据到target,
     * <br/>再通过customConverterClass赋值数据
     *
     * @param source               源数据
     * @param target               目标数据
     * @param converter            传入null
     * @param customConverterClass 自定义实现转换类
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> F convert(T source, F target, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        if (source == null || target == null) {
            return null;
        }
        copy(source, target, converter, customConverterClass);
        return target;
    }

    /**
     * 拷贝source中所有数据到target
     *
     * @param source 源数据
     * @param target 目标数据
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> F convertJPAEntity(T source, F target) {
        verificationTargetClass(target);
        LocalDateTime date = ((BaseCMP) target).getCreatedAt();
        LocalDateTime updateDate = ((BaseCMP) target).getUpdatedAt();
        convert(source, target, null, null);
        ((BaseCMP) target).setCreatedAt(date);
        ((BaseCMP) target).setUpdatedAt(updateDate);
        return target;
    }

    /**
     * 拷贝source中所有数据到target,
     * <br/>再通过customConverterClass赋值数据
     *
     * @param source               源数据
     * @param target               目标数据
     * @param converter            传入null
     * @param customConverterClass 自定义实现转换类
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月1日
     */
    public static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> F convertJPAEntity(T source, F target, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        if (source == null || target == null) {
            return null;
        }

        verificationTargetClass(target);
        LocalDateTime date = ((BaseCMP) target).getCreatedAt();//保存Entity创建时间

        copy(source, target, converter, customConverterClass);

        ((BaseCMP) target).setCreatedAt(date);//还原Entity的创建时间

        return target;
    }

    /**
     * 验证对象是否为JPA Entity
     *
     * @param target
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月9日
     */
    private static <F extends IBeanCopyPropertiesBean> void verificationTargetClass(F target) {
        if (BeanUtils.isNull(target)) {
            throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
        }
//	    	if (!target.getClass().isAnnotationPresent(Entity.class)) {
//	    		throw new BusinessException("转换数据不是JpaEntity", null, BusinessException.PARAMS_ERROR);
//			}
        if (!(target instanceof BaseCMP)) {
            throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
        }
    }

    /**
     * @param source
     * @param target
     * @param customConverterClass
     */
    @SuppressWarnings("unchecked")
    private static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> void copy(T source, F target, Class<? extends ObjectConverter> customConverterClass) {
        com.fushun.framework.bean.properties.utils.spring.BeanUtils.copyProperties(source,target);
//        ObjectConverter customConverter = getCustomConverterInstance(customConverterClass);
//        String key = source.getClass().getName() + "#" + target.getClass().getName();
//        EInvokeMethod eInvokeMethod = CACHED_INVOKE_METHOD.get(key);
//        if (eInvokeMethod == null) {
//            copy(source, target, customConverter);
//            return;
//        }
//        switch (eInvokeMethod) {
//            case ConvertFromDto:
//                customConverter.convertFromDto(source, target);
//                break;
//            case ConvertToDto:
//                customConverter.convertToDto(source, target);
//                break;
//            default:
//                break;
//        }
    }

    /**
     * 执行转换，通过判断反射
     * 并且保存缓存
     *
     * @param source
     * @param target
     * @param customConverter
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月14日
     */
    @SuppressWarnings("unchecked")
    private static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> void copy(T source, F target, ObjectConverter customConverter) {
        String key = source.getClass().getName() + "#" + target.getClass().getName();
        try {
            customConverter.convertFromDto(source, target);
            CACHED_INVOKE_METHOD.put(key, EInvokeMethod.ConvertFromDto);
            return;
        } catch (ClassCastException e) {
            logger.info(e.getMessage() + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
        }

        try {
            customConverter.convertToDto(source, target);
            CACHED_INVOKE_METHOD.put(key, EInvokeMethod.ConvertToDto);
            return;
        } catch (ClassCastException e) {
            logger.info(e.getMessage() + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new ConverterException(e, ConverterException.ConverterExceptionEnum.CONVERTER);
        }
        throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
    }

    /**
     * Private methods
     */

    private static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> void copy(T source, F target, Converter converter, Class<? extends ObjectConverter> customConverterClass) {
        com.fushun.framework.bean.properties.utils.spring.BeanUtils.copyProperties(source,target);
//        BeanCopier beanCopier = getBeanCopierInstance(source, target.getClass(), converter);
//        beanCopier.copy(source, target, converter);
//        if (customConverterClass != null) {
//            copy(source, target, customConverterClass);
//        }
    }

    private static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> BeanCopier getBeanCopierInstance(T source, Class<F> targetClass, Converter converter) {
        String key = source.getClass().getName() + "#" + targetClass.getName();
        BeanCopier beanCopier = CACHED_COPIER_MAP.get(key);
        if (beanCopier == null) {
            synchronized (CACHED_COPIER_MAP) {
                beanCopier = CACHED_COPIER_MAP.get(key);
                if (beanCopier == null) {
                    beanCopier = BeanCopier.create(source.getClass(), targetClass, converter != null);
                    CACHED_COPIER_MAP.put(key, beanCopier);
                }
            }
        }
        return beanCopier;
    }

    private static <T extends IBeanCopyPropertiesBean, F extends IBeanCopyPropertiesBean> ObjectConverter getCustomConverterInstance(Class<? extends ObjectConverter> customConverterClass) {
        if (customConverterClass == null) {
            return null;
        }
        String key = customConverterClass.getName();
        ObjectConverter converter = CACHED_CUSTOM_CONVERTER_MAP.get(key);
        if (converter == null) {
            synchronized (CACHED_CUSTOM_CONVERTER_MAP) {
                try {
                    converter = SpringContextUtil.getBean(customConverterClass);
                } catch (Exception e) {
                    logger.info(customConverterClass.getName() + " is not a component, need new instance.");
                }
                if (converter == null) {
                    try {
                        converter = customConverterClass.newInstance();
                        CACHED_CUSTOM_CONVERTER_MAP.put(key, converter);
                    } catch (InstantiationException e) {
                        throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                    } catch (IllegalAccessException e) {
                        throw new ConverterException(ConverterException.ConverterExceptionEnum.CONVERTER);
                    }
                }
            }
        }
        return converter;
    }

    enum EInvokeMethod {
        ConvertFromDto,
        ConvertToDto,
    }
}
