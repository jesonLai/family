package sys.admin.action;

import static nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.text.producer.ChineseTextProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;
import sys.SpringAnnotationDef;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller("/")
public class SimpleCaptcha {
	 private static final Logger _log= Logger.getLogger(SimpleCaptcha.class);
	  private static final String PARAM_HEIGHT = "height"; //高度 默认为50
	  private static final String PARAM_WIDTH = "width";//宽度 默认为200
	  private static final String PAEAM_NOISE="noise";//干扰线条 默认是没有干扰线条
	  private static final String PAEAM_TEXT="text";//文本
	  protected int _width = 200;
	  protected int _height = 50;
	  protected boolean _noise=true;
	  protected String _text="word:3,number:3";
	  @RequestMapping("SimpleCaptcha")
	  public void getImage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		  Builder builder=new Captcha.Builder(_width, _height);
		   //增加边框
//		   builder.addBorder();
		   
		   //干扰线
		   /*CurvedLineNoiseProducer nosi = new CurvedLineNoiseProducer(Color.green,5);
		   builder.addNoise(nosi);*/
		   //是否增加干扰线条
		   if(_noise==true)
			   builder.addNoise();
		   //----------------自定义字体大小-----------
		   //自定义设置字体颜色和大小 最简单的效果 多种字体随机显示
		   List<Font> fontList = new ArrayList<Font>();
		   fontList.add(new Font("微软 雅黑", Font.LAYOUT_NO_START_CONTEXT, 30));//可以设置斜体之类的
//		   fontList.add(new Font("Courier", Font.BOLD, 40));	
		   /*List<Color> colorList = new ArrayList<Color>();
		   colorList.add(Color.green);
		   colorList.add(Color.BLUE);
		   DefaultWordRenderer dwr=new DefaultWordRenderer(colorList,fontList);*/
		   
		   //加入多种颜色后会随机显示 字体空心
		   List<Color> colorList=new ArrayList<Color>();
		   colorList.add(Color.green);
		   colorList.add(Color.red);
		   colorList.add(Color.blue);
//		   ColoredEdgesWordRenderer   dwr= new ColoredEdgesWordRenderer (colorList,fontList);
		   DefaultWordRenderer  dwr= new DefaultWordRenderer(colorList,fontList);
		   WordRenderer wr=dwr;
		   //增加文本，默认为5个随机字符.
		   if(_text==null){
			   builder.addText();
		   }else{
			   String[]ts=_text.split(",");
			   for(int i=0;i<ts.length;i++){
				   String[] ts1=ts[i].split(":");
				   if("chinese".equals(ts1[0])){
					  builder.addText(new ChineseTextProducer(Integer.parseInt(ts1[1])),wr);
				   }else if("number".equals(ts1[0])){
					   //这里没有0和1是为了避免歧义 和字母I和O
					   char[] numberChar = new char[] { '2', '3', '4', '5', '6', '7', '8' };
					   builder.addText(new DefaultTextProducer(Integer.parseInt(ts1[1]),numberChar),wr);
				   }else if("word".equals(ts1[0])){
					   //原理同上
					   char[] numberChar = new char[] {'a','A', 'b', 'B','c','C', 'd','D',
					            'e','E', 'f', 'F','g','G', 'h','H', 'k','K', 'm','M', 'n','N', 'p','P', 'r','R', 'w','W', 'x','X', 'y','Y' };
					   builder.addText(new DefaultTextProducer(Integer.parseInt(ts1[1]),numberChar),wr);
				   }
//				   else{
//					   builder.addText(new DefaultTextProducer(Integer.parseInt(ts1[1]), null), wr);
//				   }
			   }
			   
		   }
		 
		 //--------------添加背景-------------
		   //设置背景渐进效果 以及颜色 form为开始颜色，to为结束颜色
//		   GradiatedBackgroundProducer gbp=new GradiatedBackgroundProducer();
//		   gbp.setFromColor(Color.yellow);
//		   gbp.setToColor(Color.red);
		   //无渐进效果，只是填充背景颜色
//		   FlatColorBackgroundProducer  fbp=new FlatColorBackgroundProducer(Color.BITMASK);
		   //加入网纹--一般不会用
//		   SquigglesBackgroundProducer  sbp=new SquigglesBackgroundProducer();
          // 没发现有什么用,可能就是默认的
//		   TransparentBackgroundProducer tbp = new TransparentBackgroundProducer();
		   //FlatColorBackgroundProducer fbp = new FlatColorBackgroundProducer();
		   //BufferedImage bufferedImage = ImageIO.read(new FileInputStream("E:\\eclipsespace2\\oms_portal\\WebContent\\images\\content\\table-info.png"));
		   //fbp.addBackground(bufferedImage);
		   
//		   builder.addBackground(fbp);
		   //---------装饰字体---------------
      	   // 字体边框齿轮效果 默认是3
//		   builder.gimp(new BlockGimpyRenderer(1));
		   //波纹渲染 相当于加粗
		   builder.gimp(new RippleGimpyRenderer());
		   //修剪--一般不会用
//		   builder.gimp(new ShearGimpyRenderer(Color.red));
		   //加网--第一个参数是横线颜色，第二个参数是竖线颜色
//		   builder.gimp(new FishEyeGimpyRenderer(Color.red,Color.yellow));
		   //加入阴影效果 默认3，75 
//		   builder.gimp(new DropShadowGimpyRenderer());
          //创建对象
         Captcha captcha =  builder.build();
        
         _log.debug("验证码为："+ captcha.getAnswer());
         //ImageIO.write(cnvPng2Gif(captcha.getImage()), "GIF", resp.getOutputStream());
         //req.getSession().setAttribute(NAME, captcha);
         BufferedImage newBufferedImage= null;
         BufferedImage Tmp_BufferedImage= null;
         Tmp_BufferedImage = new BufferedImage(captcha.getImage()
					.getWidth(), captcha.getImage().getHeight(), BufferedImage.TYPE_INT_RGB);
			Tmp_BufferedImage.createGraphics().drawImage(captcha.getImage(), 0, 0, Color.WHITE, null);
     	response.setContentType("image/gif");
     	ImageIO.write(Tmp_BufferedImage, "GIF", response.getOutputStream());
		
//       req.getSession().setAttribute(name, captcha.getAnswer());  
     	session.setAttribute(NAME, captcha);  
	  }
	  @RequestMapping("/isCaptcha")
	  @ResponseBody boolean isCaptcha(@RequestParam(value="captchaVal",defaultValue="")String captchaVal,HttpSession session){
		  Captcha captchaed=(Captcha)session.getAttribute(NAME);
		  String captchaedVa=captchaed==null?null:captchaed.getAnswer();
		  return captchaVal.equalsIgnoreCase(captchaedVa);
	  } 
}
