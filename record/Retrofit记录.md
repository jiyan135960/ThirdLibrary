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