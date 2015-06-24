import cn.edu.fudan.se.bean.Server;
import cn.edu.fudan.se.proxy.Main;
import cn.edu.fudan.se.parser.Parser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dawnwords on 2015/6/24.
 */

public class TestParser {

    @Test
    public void testParse() {
        List<Server> servers = new Parser(Main.SERVER_URL).parse();
        assertEquals(servers.size(), 1);
        System.out.println(servers.get(0));
    }
}
