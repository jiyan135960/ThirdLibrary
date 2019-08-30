# Retrofit 记录

## MockRetrofit

### 使用流程

1. 创建需要的MockT,实现接口T:MockT:T
2. 创建Retrofit:Retrofit.Builder().baseUrl().build();
3. 创建NetworkBehavior:NetworkBehavior.create();
4. 创建MockRetrofit:MockRetrofit.Builder(retrofit).networkBehavior(behavior).build();
5. 创建BehaviorDelegate:MockRetrofit.create(T::class.java);
6. 创建MockT t = MockT(deleget);

