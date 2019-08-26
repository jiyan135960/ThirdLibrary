# Gson记录

## 转为Json

### 转换Json方法调用

Gson().toJson()

### 转换Json实现流程

1. Gson: 调用toJson(Object)
2. Gson: 调用toJson(object,Type,JsonWritter)
   1. Gson$toJson: 通过getAdapter(TypeToken.get(Type))获得TypeAdapter
      1. Gson$getAdapter(): 通过TypeToken.get(Type)获得TypeToken
         1. TypeToken: 新建TypeToken\<Object>(Type);
         2. TypeToken: 调用\$Gson$Types.canonicalize(Type)获得规范化类型(下方详解)并赋值给自身Type
         3. TypeToken: 调用$Gson$Types.getRawType(Type)获得RawType并赋值给自身rawType返回值为Class<? super T> rawType
         4. TypeToken: 调用Type.hashCode()获得hashCode
   2. Gson$toJson(): 设置JsonWriter参数
   3. Gson$toJson(): 返回writer.toString()
3. Gson:返回结果

## 转为实体类

### 转换实体类方法调用

Gson().from(String,Type/Class\<T>)

### 转换实体类实现流程

1. Gson: 调用fromJson(String,Type)
2. Gson: 调用fromJson(Reader,Type)
3. Gson: 调用fromJson(JsonReader,Type)
   1. Gson$fromJson: 调用TypeToken.get(Type)获得TypeToken
      1. TypeToken: 通过TypeToken.get(typeOfSrc)获得TypeToken
         1. TypeToken: 新建TypeToken\<Object>(Type);
         2. TypeToken: 调用\$Gson$Types.canonicalize(Type)获得规范化类型(下方详解)并赋值给自身Type
         3. TypeToken: 调用$Gson$Types.getRawType(Type)获得RawType并赋值给自身rawType返回值为Class<? super T> rawType
         4. TypeToken: 调用Type.hashCode()获得hashCode
   2. Gson$toJson(): 设置JsonWriter参数
   3. Gson$fromJson: 调用getAdapter(TypeToken)获得TypeAdapter
      1. Gson$getAdapter(): 判断从缓存中获取还是正常获取
      2. Gson$getAdapter(): 创建FutureTypeAdapter
      3. Gson$getAdapter(): 返回TypeAdapter

## Gson的Types规范化:\$Gson$Types.canonicalize(Type)

1. \$Gson$Types: 判断是否是Class,是则返回
2. \$Gson$Types: 判断是否是ParameterizedType(参数化类型),是则返回
   1. ParameterizedTypeImpl: 创建参数化类型实例 ParameterizedTypeImpl()
   2. ParameterizedTypeImpl$ParameterizedTypeImpl(): 设置ownerType,rawType,typeArguments(通过调用canonicalize()循环获得)并返回ParameterizedTypeImpl
3. \$Gson$Types: 判断是否是GenericArrayType(数组类型),是则返回
4. \$Gson$Types: 判断是否是WildcardType(通配符类型),是则返回
   1. WildcardTypeImpl: 创建通配符类型 WildcardTypeImpl()
   2. WildcardTypeImpl: 获得上下限制(upperBound,lowerBound),方式依然通过调用canonicalize()
5. \$Gson$Types: 否则返回Type,表示这个类型不能序列化,是则返回
