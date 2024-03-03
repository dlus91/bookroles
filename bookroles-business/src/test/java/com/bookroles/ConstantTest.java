package com.bookroles;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import org.junit.*;

import java.util.List;
import java.util.Map;


/**
 * @Author: dlus91
 * @Date: 2023/9/20 17:59
 */
public class ConstantTest {

    public void initData(){
        String bookClickTotalStr = "{'1':{'count':2,'timestamp':1695199784790},'2':{'count':3,'timestamp':1695199854115},'3':{'count':2,'timestamp':1695199822696},'4':{'count':1,'timestamp':1695199798731},'5':{'count':2,'timestamp':1695199810102},'6':{'count':3,'timestamp':1695199807171},'7':{'count':2,'timestamp':1695199803902},'8':{'count':0,'timestamp':0},'10':{'count':1,'timestamp':1695199742445},'11':{'count':1,'timestamp':1695199746685}}";
        Map<Integer, Map<String, String>> bookClickTotalMap = JSONObject.parseObject(bookClickTotalStr, new TypeReference<Map<Integer, Map<String, String>>>() {
        });
        Constant.BOOK_CLICK_LAST_RECORD = bookClickTotalMap;

        String bookDownloadTotalStr = "{'1':{'count':0,'timestamp':0},'2':{'count':1,'timestamp':1695199873161},'3':{'count':0,'timestamp':0},'4':{'count':0,'timestamp':0},'5':{'count':0,'timestamp':0},'6':{'count':0,'timestamp':0},'7':{'count':0,'timestamp':0},'8':{'count':0,'timestamp':0},'10':{'count':0,'timestamp':0},'11':{'count':1,'timestamp':1695199755238}}";
        Map<Integer, Map<String, String>> bookDownloadTotalMap = JSONObject.parseObject(bookDownloadTotalStr, new TypeReference<Map<Integer, Map<String, String>>>() {
        });
        Constant.BOOK_DOWNLOAD_LAST_RECORD = bookDownloadTotalMap;

        String bookHtmlRecordStr = "[{'file_name':'7093144164651008.html', 'book_id':'1', 'timestamp':'1695188737357'}, " +
                "{'file_name':'7093144202399744.html', 'book_id':'2', 'timestamp':'1695188737365'}, " +
                "{'file_name':'7093144223371264.html', 'book_id':'3', 'timestamp':'1695188737369'}, " +
                "{'file_name':'7093144235954176.html', 'book_id':'4', 'timestamp':'1695188737372'}, " +
                "{'file_name':'7093144248537088.html', 'book_id':'5', 'timestamp':'1695188737375'}, " +
                "{'file_name':'7093144261120000.html', 'book_id':'6', 'timestamp':'1695188737378'}, " +
                "{'file_name':'7093144269508608.html', 'book_id':'7', 'timestamp':'1695188737380'}, " +
                "{'file_name':'7093144282091520.html', 'book_id':'8', 'timestamp':'1695188737382'}, " +
                "{'file_name':'7093144290480128.html', 'book_id':'9', 'timestamp':'1695188737385'}, " +
                "{'file_name':'7093144298868736.html', 'book_id':'10', 'timestamp':'1695188737387'}, " +
                "{'file_name':'7093144311451648.html', 'book_id':'11', 'timestamp':'1695188737390'}, " +
                "{'file_name':'7093144319840256.html', 'book_id':'12', 'timestamp':'1695188737392'}, " +
                "{'file_name':'7093144332423168.html', 'book_id':'13', 'timestamp':'1695188737394'}, " +
                "{'file_name':'7093144340811776.html', 'book_id':'14', 'timestamp':'1695188737396'}, " +
                "{'file_name':'7093144349200384.html', 'book_id':'15', 'timestamp':'1695188737398'}, " +
                "{'file_name':'7093144357588992.html', 'book_id':'16', 'timestamp':'1695188737400'}," +
                "{'file_name':'7093144365977600.html', 'book_id':'17', 'timestamp':'1695188737402'}," +
                "{'file_name':'7093144374366208.html', 'book_id':'18', 'timestamp':'1695188737404'}," +
                "{'file_name':'7093144382754816.html', 'book_id':'19', 'timestamp':'1695188737406'}," +
                "{'file_name':'7093144391143424.html', 'book_id':'20', 'timestamp':'1695188737409'}]";
        List<Map<String, String>> bookHtmlRecordList = JSONObject.parseObject(bookHtmlRecordStr, new TypeReference<List<Map<String, String>>>() {});
        Constant.setBookHtmlRecordIndex(bookHtmlRecordList);

        String bookStr = "[{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'2'}, " +
                "{'archive_type_name':'成功学',  'image':'1693381712215.jpg', 'author':'李尚龙','publish_house':'浙江出版社',   'key_words':'持续 成长', 'download_format':'epub', 'publish_month':'2021/10', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4',   'upload_time':'1小时前','name':'持续成长', 'archive_type_id':'13', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'3'}," +
                "{'archive_type_name':'认知科学', 'image':'1693383144955.jpg', 'author':'朱建军 曹昱','publish_house':'中信出版社','key_words':'情绪 词典', 'download_format':'epub', 'publish_month':'2019/08', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。'</p>\",'score':'4',  'upload_time':'昨天',  'name':'情绪词典', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'4'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周倩', 'name':'技术陷阱5','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'5'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周倩', 'name':'技术陷阱6','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'6'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周倩', 'name':'技术陷阱7','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'7'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周倩', 'name':'技术陷阱8','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'8'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周前', 'name':'技术陷阱9','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'9'}," +
                "{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生10', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'10'}," +
                "{'archive_type_name':'成功学',  'image':'1693381712215.jpg', 'author':'李尚龙','publish_house':'浙江出版社',   'key_words':'持续 成长', 'download_format':'epub', 'publish_month':'2021/10', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4',   'upload_time':'1小时前','name':'持续成长11', 'archive_type_id':'13', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'11'}," +
                "{'archive_type_name':'认知科学', 'image':'1693383144955.jpg', 'author':'朱建军 曹昱','publish_house':'中信出版社','key_words':'情绪 成长', 'download_format':'epub', 'publish_month':'2019/08', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4',   'upload_time':'昨天',  'name':'情绪词典12', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'12'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5',  'upload_time':'2周前', 'name':'技术陷阱13', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'13'}," +
                "{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5',   'upload_time':'2天前', 'name':'价值共生14', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'14'}," +
                "{'archive_type_name':'成功学',  'image':'1693381712215.jpg', 'author':'李尚龙','publish_house':'浙江出版社',   'key_words':'持续 成长', 'download_format':'epub', 'publish_month':'2021/10', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4',   'upload_time':'1小时前','name':'持续成长15', 'archive_type_id':'13', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'15'}," +
                "{'archive_type_name':'认知科学', 'image':'1693383144955.jpg', 'author':'朱建军 曹昱','publish_house':'中信出版社','key_words':'情绪 成长', 'download_format':'epub', 'publish_month':'2019/08', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4',   'upload_time':'昨天,'  'name':'情绪词典16', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'16'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周前', 'name':'技术陷阱17', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'17'}]";
        List<Map<String, String>> bookList = JSONObject.parseObject(bookStr, new TypeReference<List<Map<String, String>>>() {});
        Constant.setBookIndex(bookList);

        String bookKeywordStr = "[" +
                "{'extend_key_words':'陷阱1 失业 技术 共生 陷阱 情绪 词典 创造性创新 技术1 价值 替代性创新 工业革命', 'name':'技术陷阱', 'key_words':'技术 陷阱 工业革命 创造性创新 替代性创新 失业 陷阱1', 'id':'1', 'extend_ids':'1 13 5 6 17 7 18 8 9 20'}," +
                "{'extend_key_words':'持续 成长 共生 价值 创造性创新', 'name':'价值共生', 'key_words':'价值 共生', 'id':'2', 'extend_ids':'2 3 14 18 10'}," +
                "{'extend_key_words':'持续 成长 共生 价值 创造性创新', 'name':'持续成长', 'key_words':'持续 成长 价值', 'id':'3', 'extend_ids':'11 2 3 14 15 18 19 10'}," +
                "{'extend_key_words':'失业 情绪 词典', 'name':'情绪词典', 'key_words':'情绪 词典', 'id':'4', 'extend_ids':'12 4 16 20'}, " +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱5', 'key_words':'技术 陷阱', 'id':'5', 'extend_ids':'1 13 5 17 7 8 9'}, " +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 技术1 替代性创新 工业革命', 'name':'技术陷阱6', 'key_words':'技术1 陷阱1', 'id':'6', 'extend_ids':'1 13 5 6 17 7 8 9'}," +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱7', 'key_words':'技术 陷阱', 'id':'7', 'extend_ids':'1 13 5 17 7 8 9'}," +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱8', 'key_words':'技术 陷阱', 'id':'8', 'extend_ids':'1 13 5 17 7 8 9'}," +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱9', 'key_words':'技术 陷阱', 'id':'9', 'extend_ids':'1 13 5 17 7 8 9'}," +
                "{'extend_key_words':'持续 成长 共生 价值 创造性创新', 'name':'价值共生10', 'key_words':'价值 共生', 'id':'10', 'extend_ids':'2 3 14 18 10'}," +
                "{'extend_key_words':'持续 成长 价值', 'name':'持续成长11', 'key_words':'持续 成长', 'id':'11', 'extend_ids':'11 3 15 19'}," +
                "{'extend_key_words':'失业 情绪 词典', 'name':'情绪词典12', 'key_words':'情绪', 'id':'12', 'extend_ids':'12 4 16 20'}," +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱13', 'key_words':'技术 陷阱', 'id':'13', 'extend_ids':'1 13 5 17 7 8 9'}," +
                "{'extend_key_words':'持续 成长 共生 价值 创造性创新', 'name':'价值共生14', 'key_words':'价值 共生', 'id':'14', 'extend_ids':'2 3 14 18 10'}," +
                "{'extend_key_words':'持续 成长 价值', 'name':'持续成长15', 'key_words':'持续 成长', 'id':'15', 'extend_ids':'11 3 15 19'}," +
                "{'extend_key_words':'失业 情绪 词典', 'name':'情绪词典16', 'key_words':'情绪', 'id':'16', 'extend_ids':'12 4 16 20'}," +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 创造性创新 替代性创新 工业革命', 'name':'技术陷阱17', 'key_words':'技术 陷阱', 'id':'17', 'extend_ids':'1 13 5 17 7 8 9'}," +
                "{'extend_key_words':'陷阱1 持续 失业 技术 成长 共生 陷阱 创造性创新 价值 替代性创新 工业革命', 'name':'价值共生18', 'key_words':'价值 共生 创造性创新', 'id':'18', 'extend_ids':'1 2 3 14 18 10'}," +
                "{'extend_key_words':'持续 成长 价值', 'name':'持续成长19', 'key_words':'持续 成长', 'id':'19', 'extend_ids':'11 3 15 19'}, " +
                "{'extend_key_words':'陷阱1 失业 技术 陷阱 情绪 词典 创造性创新 替代性创新 工业革命', 'name':'情绪词典20', 'key_words':'情绪 词典 失业', 'id':'20', 'extend_ids':'1 12 4 16 20'}]";
        List<Map<String, String>> bookKeywordList = JSONObject.parseObject(bookKeywordStr, new TypeReference<List<Map<String, String>>>() {});
        Constant.setBookKeywordsIndex(bookKeywordList);

        Constant.setBookDownloadUrlIndex(bookList);
    }

    @BeforeClass
    public static void setUpClass() {


    }

    @Before
    public void setUp() {
        //初始化数据
        initData();
    }

    @Test
    public void testSetInstanceServletContext() throws Exception {
        Constant.setInstanceServletContext(null);
    }
//
    @Test
    public void testGetBookClickLastRecord() throws Exception {
        Map<String, String> result = Constant.getBookClickLastRecord(1);
        Assert.assertEquals(Map.of("count", "2","timestamp","1695199784790"), result);
    }

    @Test
    public void testUpdateBookCLickLastRecord() throws Exception {
        Constant.updateBookCLickLastRecord(1);
    }

    @Test
    public void testUpdateBookCLickLastRecord2() throws Exception {
        Constant.updateBookCLickLastRecord(1, 3, System.currentTimeMillis());
    }

    @Test
    public void testGetBookDownloadLastRecord() throws Exception {
        Map<String, String> result = Constant.getBookDownloadLastRecord(2);
        Assert.assertEquals(Map.of("count", "1","timestamp","1695199873161"), result);
    }

    @Test
    public void testUpdateBookDownloadLastRecord() throws Exception {
        Constant.updateBookDownloadLastRecord(1);
    }

    @Test
    public void testUpdateBookDownloadLastRecord2() throws Exception {
        Constant.updateBookDownloadLastRecord(1, 1, System.currentTimeMillis());
    }

    @Test
    public void testGetBookIndex() throws Exception {
        List<Map<String, String>> result = Constant.getBookIndex();
        Assert.assertEquals("2", result.get(0).get("id"));
    }

    @Test
    public void testSetBookIndex() throws Exception {
        String bookStr = "[{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'2'}]";
        List<Map<String, String>> bookList = JSONObject.parseObject(bookStr, new TypeReference<List<Map<String, String>>>() {});
        boolean result = Constant.setBookIndex(bookList);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFlushBookNameIndex() throws Exception {
        Constant.flushBookNameIndex();
    }

    @Test
    public void testSelectBookIndexById() throws Exception {
        Map<String, String> result = Constant.selectBookIndexById("2");
        Assert.assertEquals("2", result.get("id"));
    }

    @Test
    public void testSelectBookIndexByIdArray() throws Exception {
        List<Map<String, String>> result = Constant.selectBookIndexByIdArray(new String[]{"2","4"});
        Assert.assertEquals("价值共生", result.get(0).get("name"));
    }

    @Test
    public void testSelectBookIndexByIds() throws Exception {
        List<Map<String, String>> result = Constant.selectBookIndexByIds("3","5");
        Assert.assertEquals("持续成长", result.get(0).get("name"));
    }

    @Test
    public void testSelectBookIndexData() throws Exception {
        List<Map<String, String>> result = Constant.selectBookIndexData(1);
        Assert.assertEquals(4, result.size());
        Assert.assertEquals("价值共生", result.get(0).get("name"));
    }

    @Test
    public void testSelectBookIndexData2() throws Exception {
        List<Map<String, String>> result = Constant.selectBookIndexData(1, 6);
        Assert.assertEquals(6, result.size());
        Assert.assertEquals("价值共生", result.get(0).get("name"));
    }

    @Test
    public void testSelectBookKeyWordsIndexData() throws Exception {
        List<Map<String, String>> result = Constant.selectBookKeyWordsIndexData("价值", 1);
        Assert.assertEquals(4, result.size());
        Assert.assertEquals("价值共生", result.get(0).get("name"));
    }

    @Test
    public void testSelectBookKeyWordsIndexData2() throws Exception {
        List<Map<String, String>> result = Constant.selectBookKeyWordsIndexData("技术", 1, 6);
        Assert.assertEquals("技术陷阱17", result.get(5).get("name"));
    }

    @Test
    public void testSelectBookAuthorIndexData() throws Exception {
        List<Map<String, String>> result = Constant.selectBookAuthorIndexData("李尚龙", 1);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("持续成长11", result.get(1).get("name"));
    }

    @Test
    public void testSelectBookAuthorIndexData2() throws Exception {
        List<Map<String, String>> result = Constant.selectBookAuthorIndexData("弗雷?卡尔", 1, 6);
        Assert.assertEquals(6, result.size());
        Assert.assertEquals("技术陷阱6", result.get(1).get("name"));
    }

    @Test
    public void testSelectBookArchiveTypeIndexData() throws Exception {
        List<Map<String, String>> result = Constant.selectBookArchiveTypeIndexData(14, 1);
        Assert.assertEquals(4, result.size());
        Assert.assertEquals("情绪词典", result.get(1).get("name"));
    }

    @Test
    public void testSelectBookArchiveTypeIndexData2() throws Exception {
        List<Map<String, String>> result = Constant.selectBookArchiveTypeIndexData(14, 1, 6);
        Assert.assertEquals(6, result.size());
        Assert.assertEquals("技术陷阱6", result.get(3).get("name"));
    }

    @Test
    public void testGetBookDownloadUrlIndex() throws Exception {
        Map<String, String> result = Constant.getBookDownloadUrlIndex();
        Assert.assertEquals(16, result.size());
        Assert.assertEquals("https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr", result.get("12"));
    }

    @Test
    public void testSetBookDownloadUrlIndex() throws Exception {
        String bookStr = "[{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'2'}]";
        List<Map<String, String>> bookList = JSONObject.parseObject(bookStr, new TypeReference<List<Map<String, String>>>() {});
        boolean result = Constant.setBookDownloadUrlIndex(bookList);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFlushBookDownloadUrlIndex() throws Exception {
        Constant.flushBookDownloadUrlIndex();
    }

    @Test
    public void testGetBookKeywordsIndex() throws Exception {
        List<Map<String, String>> result = Constant.getBookKeywordsIndex();
        Assert.assertEquals(20, result.size());
        Assert.assertEquals("持续成长", result.get(2).get("name"));
        Assert.assertEquals("11 2 3 14 15 18 19 10", result.get(2).get("extend_ids"));
    }

    @Test
    public void testSelectBookKeywordsIndexById() throws Exception {
        Map<String, String> result = Constant.selectBookKeywordsIndexById("2");
        Assert.assertEquals("价值共生", result.get("name"));
        Assert.assertEquals("2 3 14 18 10", result.get("extend_ids"));
        Assert.assertEquals("持续 成长 共生 价值 创造性创新", result.get("extend_key_words"));
    }

    @Test
    public void testSetBookKeywordsIndex() throws Exception {
        String bookKeywordStr = "[" +
                "{'extend_key_words':'陷阱1 失业 技术 共生 陷阱 情绪 词典 创造性创新 技术1 价值 替代性创新 工业革命', 'name':'技术陷阱', 'key_words':'技术 陷阱 工业革命 创造性创新 替代性创新 失业 陷阱1', 'id':'1', 'extend_ids':'1 13 5 6 17 7 18 8 9 20'}]";
        List<Map<String, String>> bookKeywordList = JSONObject.parseObject(bookKeywordStr, new TypeReference<List<Map<String, String>>>() {});
        boolean result = Constant.setBookKeywordsIndex(bookKeywordList);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testGetExtendBookKeyWordsAndBooksFromData() throws Exception {
        String bookStr = "[" +
                "{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'2'}," +
                "{'archive_type_name':'认知科学', 'image':'1693380712834.jpg', 'author':'弗雷?卡尔','publish_house':'中信出版社', 'key_words':'技术 陷阱', 'download_format':'epub', 'publish_month':'2020/06', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'4.5', 'upload_time':'2周前', 'name':'技术陷阱9','archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'9'}," +
                "{'archive_type_name':'认知科学', 'image':'1693382935689.jpg', 'author':'陈春花','publish_house':'中国人民出版社', 'key_words':'价值 共生', 'download_format':'epub', 'publish_month':'2022/01', 'content_intro':\"<p>本书系统而全面地回顾了近几百年<a target:'_blank' href:'https://baike.baidu.com/item/技术/13014499?fromModule:lemma_inlink'>技术</a>进步的历史，以及它如何从根本上改变了社会成员之间的经济和政治权力分配。</p><p>本书作者将带领读者们遍览各个时代技术进步对人们工作形态的影响，揭示不同时代“打工人”的处境，并最终将目光转向未来，试图分析当前的AI革命将对我们的工作造成何种影响，以及我们该如何做出应对。作者力图说明，技术进步对收入造成何种影响，将决定人们对它的态度。 </p><p><a target:'_blank' href:'https://baike.baidu.com/item/工业革命/895?fromModule:lemma_inlink'>工业革命</a>是历史上的重大时刻，但当时几乎没有人意识到它的巨大后果。</p><p>正如本书所表明的那样，工业革命从长远来看创造了前所未有的财富和繁荣，但机械化的直接后果对大量人口来说是毁灭性的。中等收入岗位减少，工资停滞不前，劳动收入占比下降，利润激增，经济不平等加剧。</p><p>本书作者卡尔·贝内迪克特·弗雷证明，这些趋势大体上反映了我们现在这个始于计算机革命的自动化时代的趋势。正如工业革命最终为社会带来非同寻常的利益一样，人工智能系统也有潜力做到这一点。</p><p>《技术陷阱》表明，在眼下这场新的技术革命中，过去的教训可以帮助我们更有效地面对现在的状况。</p>\", 'score':'3.5', 'upload_time':'2天前', 'name':'价值共生10', 'archive_type_id':'14', 'download_url':'https://pan.baidu.com/s/1kcy4jmJSgXdtEpOyGjRtMg?pwd:7ihr', 'id':'10'}" +
                "]";
        List<Map<String, String>> bookList = JSONObject.parseObject(bookStr, new TypeReference<List<Map<String, String>>>() {});
        List<Map<String, String>> result = Constant.getExtendBookKeyWordsAndBooksFromData(bookList);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("价值共生10", result.get(2).get("name"));
        Assert.assertEquals("价值 共生", result.get(2).get("key_words"));
        Assert.assertEquals("共生 价值", result.get(2).get("extend_key_words"));
        Assert.assertEquals("2 14 10", result.get(2).get("extend_ids"));

    }

    @Test
    public void testGetExtendBookKeyWordsAndBooksFromBookIndex() throws Exception {
        List<Map<String, String>> result = Constant.getExtendBookKeyWordsAndBooksFromBookIndex();
        Assert.assertEquals(16, result.size());
        Assert.assertEquals("情绪词典", result.get(2).get("name"));
        Assert.assertEquals("情绪 词典", result.get(2).get("key_words"));
        Assert.assertEquals("成长 情绪 词典", result.get(2).get("extend_key_words"));
        Assert.assertEquals("12 4 16", result.get(2).get("extend_ids"));
    }

    @Test
    public void testFlushBookKeywordsIndex() throws Exception {
        Constant.flushBookKeywordsIndex();
    }

    @Test
    public void testGetBookHtmlRecordIndex() throws Exception {
        String bookHtmlRecordStr = "{'file_name':'7093144164651008', 'book_id':'1', 'timestamp':'1695188737357'}";
        List<Map<String, String>> bookHtmlRecordList = JSONObject.parseObject(bookHtmlRecordStr, new TypeReference<List<Map<String, String>>>() {});
        Constant.setBookHtmlRecordIndex(bookHtmlRecordList);
        Map<String, String> BookHtmlRecordMap = Constant.getBookHtmlRecordIndex();
        Assert.assertEquals(Map.of("1","7093144164651008"), BookHtmlRecordMap);
    }

    @Test
    public void testSetBookHtmlRecordIndex() throws Exception {
        String bookHtmlRecordStr = "[{'file_name':'7093144164651008.html', 'book_id':'1', 'timestamp':'1695188737357'}]";
        List<Map<String, String>> bookHtmlRecordList = JSONObject.parseObject(bookHtmlRecordStr, new TypeReference<List<Map<String, String>>>() {});
        boolean result = Constant.setBookHtmlRecordIndex(bookHtmlRecordList);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFlushBookHtmlRecordIndex() throws Exception {
        Constant.flushBookHtmlRecordIndex();
    }

    @Test
    public void testGetRealIp() throws Exception {
        String result = Constant.getRealIp(null);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testUnicodeEncode() throws Exception {
        String result = Constant.unicodeEncode("中文哈哈");
        Assert.assertEquals("\\u4e2d\\u6587\\u54c8\\u54c8", result);
    }

    @Test
    public void testUnicodeDecode() throws Exception {
        String result = Constant.unicodeDecode("\\u4e2d\\u6587\\u54c8\\u54c8");
        Assert.assertEquals("中文哈哈", result);
    }
}
