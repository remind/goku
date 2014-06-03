package org.remind.goku.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.remind.goku.annotations.Default;

/**
 * class操作工具类
 * @author remind
 *
 */
public class ClassUtil {
	
	/**
	 * 从指定包路径获取class
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String packageName, ClassFilter classFilter) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageDirName = packageName.replace('.', File.separatorChar);
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				File dir = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
				if (url.getProtocol().equals("file")) {
					Set<File> fileSet = DirectoryUtil.getFileByRecursive(dir, new FileFilter() {
						@Override
						public boolean accept(File pathname) {
							if (pathname.isDirectory() || pathname.getName().endsWith(".class")) {
								return true;
							}
							return false;
						}
					});
					for(File file : fileSet) {
						String className = packageName + file.getPath().substring(dir.getPath().length()).replace(File.separatorChar, '.');
						className = className.substring(0, className.length() - 6);//去掉后面的.class
						try {
							Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(className);
							if (classFilter.accept(cls)) {
								classes.add(cls);
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
    /**
     * 得到某个方法的参数名及类型
     * 由于使用的是javaassist，所以必须得有.class文件，如一系jdk类就无法获取
     * @param clazz
     * @param method
     * @return
     */
    public static List<MethodParamInfo> getMethodParam(Class<?> clazz, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        List<MethodParamInfo> list = new ArrayList<MethodParamInfo>();
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(clazz));
            CtClass cc = pool.get(clazz.getName());
            String[] paramTypeNames = new String[method.getParameterTypes().length];
            for (int i = 0; i < paramTypes.length; i++) {
                paramTypeNames[i] = paramTypes[i].getName();
            }

            CtMethod cm = cc.getDeclaredMethod(method.getName(), pool.get(paramTypeNames));
            MethodInfo methodInfo = cm.getMethodInfo();// 使用javaassist的反射方法获取方法的参数名
            LocalVariableAttribute attr = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                throw new RuntimeException("class:" + clazz.getName()
                        + ", have no LocalVariableTable, please use javac -g:{vars} to compile the source file");
            }
            int startIndex = getStartIndex(attr);
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            
            for (int i = 0; i < cm.getParameterTypes().length; i++) {
            	MethodParamInfo info = new MethodParamInfo(attr.variableName(startIndex + i + pos));
            	info.setType(method.getParameterTypes()[i]);
            	Object[] temp = cm.getParameterAnnotations()[i];
            	for (int j = 0; j < temp.length; j++) {
            		if ((temp[j] instanceof Default)) {
            			info.setDefaultValue(((Default)temp[0]).value());
            		}
            	}
            	list.add(info);
            }

        } catch (NotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private static int getStartIndex(LocalVariableAttribute attr) {
        int startIndex = 0;
        for (int i = 0; i < attr.length(); i++) {
            if ("this".equals(attr.variableName(i))) {
                startIndex = i;
                break;
            }
        }
        return startIndex;
    }
    
	/**
	 * 类过滤器
	 * @author remind
	 *
	 */
	public interface ClassFilter {
		/**
		 * 为true就接受
		 * @param cls
		 * @return
		 */
		public boolean accept(Class<?> cls);
	}
}
