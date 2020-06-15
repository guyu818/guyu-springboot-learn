
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 对快速开始代码的一些分析
 */
public class Quickstart {
    //日志输出
    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {
        //3步走
        //通过工厂从配置文件中读取数据创建一个实例
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        //这个SecurityManager实例编程JVM的一个单例,但实际上大多数项目都不会这么做,一般情况都会配置与于Web.xml或WebApp中
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前用户对象
        Subject currentUser = SecurityUtils.getSubject();
        //获取用户对象的Session,注意的是这个Session不是我们web项目中的HttpSession
        Session session = currentUser.getSession();

        //假数据用于测试
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }




        if (!currentUser.isAuthenticated()) {//判断当前用户是否被认证,即是否已经登录
            //通过认证的话,通过账号和密码会生成一个token(俗称令牌)
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            //设置记住我功能
            token.setRememberMe(true);
            try {
                //执行了登陆操作
                currentUser.login(token);
            } catch (UnknownAccountException uae) {//未知的账户异常,一般为用户不存在
                log.info("用户名不存在异常==========There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {//密码错误
                log.info("密码错误异常==========Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {//锁定用户,一般用于多次密码错误
                log.info("多次密码错误异常==========The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException ae) {//认证异常
                log.info("认证异常");
            }
        }

        //获取当前用户的信息currentUser.getPrincipal()
        log.info("获取当前用户的信息==========User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //测试用户是由为该角色
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //测试角色第二种中的通配符形式
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //测试角色第三种中无通配符
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //注销
        currentUser.logout();

        //结束启动
        System.exit(0);
    }
}
