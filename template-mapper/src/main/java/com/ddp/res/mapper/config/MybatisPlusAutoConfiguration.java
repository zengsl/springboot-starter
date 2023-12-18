package com.ddp.res.mapper.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * mybatis-plus自动配置
 *
 * @author zzz
 * @date 2023/11/25 16:12
 */
@AutoConfiguration
@MapperScan("com.ddp.res.mapper")
public class MybatisPlusAutoConfiguration {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"sys_user".equalsIgnoreCase(tableName);
            }
        });
//        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除 https://baomidou.com/pages/c571bc/#blockattackinnerinterceptor
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());

        // 如果配置多个插件,切记分页最后添加
        // 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
