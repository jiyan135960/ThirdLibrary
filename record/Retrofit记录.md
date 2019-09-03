# Retrofit 记录

## MockRetrofit

### MockRetrofit使用流程

1. 创建Retrofit:Retrofit.Builder().baseUrl().build();
2. 创建NetworkBehavior:NetworkBehavior.create();
3. 创建MockRetrofit:MockRetrofit.Builder(retrofit).networkBehavior(behavior).build();
4. 创建需要的MockT,实现接口T:MockT:T
5. 创建BehaviorDelegate:MockRetrofit.create(T::class.java);
6. 创建MockT t = MockT(deleget);

### MockRetrofit包架构

- BehaviorCall
- BehaviorDelegate
- Calls
- MockRetrofit
- MockRetrofitIOException
- NetworkBehavior
- package-info

未完成

## Retrofit

### Retrofit使用流程

1. 创建Retrofit
2. 创建OkHttpClient
3. 创建Service
4. 调用返回

### Retrofit包架构

1. http: annotation
2. internal
3. 使用类

### Retrofit创建Service流程

1. Retrofit.create(Service)
2. 判断是否开启 validate eagerly(默认为false)
3. 使用动态代理创建Service
   1. 判断方法是否来自对象,如果来自对象则直接调用
   2. 判断是否是平台默认方法(默认为false,Android24以上会判断)
   3. 调用loadServiceMethod(method).invoke(args != null ? args : emptyArgs)
      1. 判断是否在serviceMethodCache中是否存在,如果有则返回
      2. 同步代码块创建
      3. 调用ServiceMethod.parseAnnotations(retrofit,method) 进行ServiceMethod创建,并放入缓存
         1. **调用RetrofitFactory.parseAnnotations(retrofit,method);生成RetrofitFactory**
            1. 通过Builder创建RequestFactory
            2. 循环methodAnnotations,调用parseMethodAnnotations(annotation);解析方法注解
               1. 判断顺序:DELETE-GET-HEAD-PATCH-POST-PUT-OPTIONS-HTTP-Multipart-FormUrlEncoded
            3. 解析参数和其注解并生成相应的ParameterHandler;
               1. 循环某一个参数的所有注解,只能有一个retrofit的注解,但是可以有其他自定义注解,解析到其他注解就继续,解析到两个Retrofit注解就报错
               2. 调用parseParameterAnnotation(index,parameterType,annotations,annotation);生成ParameterHandler
                  1. 参数注解类型判断,顺序为:Url-Path-Query-QueryName-QueryMap-Header-HeaderMap-Field-FieldMap-Part-PartMap-Body-Tag(解析完成返回相应ParameterHandler或者null)
                  2. 如果是Continuation类型则为kotlinSuspendFunction,要做特殊处理
            4. 返回RequestFactory
         2. 调用method.getGenericReturnType();获取方法返回值类型
         3. 调用HttpServiceMethod.parseAnootations(retrofit,method,requestFactory);来生成ServiceMethod
            1. 判断是否是kotlinSuspendFunction,如果是通过工具类实现返回值类型适配,如果不是直接返回method.getGenericRetrunType()的类型
            2. 根据上方获得的adapterType,调用createCallAdapter(retrofit,method,adapterType,annotations)来创建CallAdapter;(实际调用: retrofit.callAdapter(returnType, annotations);)
            3. 通过callAdapter获得reponseType
            4. 创建Converter;(实际调用:retrofit.responseBodyConverter(responseType, annotations);)
            5. 通过retrofit.callFactory获得Okhttp的callFactory
            6. 如果不是kotlinSuspendFunction正常创建**CallAdapted**(继承于HttpServiceMethod)
            7. 如果有想要的返回值返回SuspendForResponse
            8. 否则返回SuspendForBody

### Retrofit 执行调用

