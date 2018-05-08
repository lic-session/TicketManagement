package pers.gene.ticketmanagement.web;



import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping("/search")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public @ResponseBody String search(@RequestParam(required=true,defaultValue="1") Integer page, HttpServletRequest request, Ticket ticket){
        String checkin = ticket.getCheckin();
        String checkout = ticket.getCheckout();
            //表单提交自动把date 类型转为String
        Date startTime = ticket.getStartTime();
//        Integer coount =
//        convert(startTime);
//        Customer customer = (Customer) request.getSession().getAttribute("customer");
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】

        PageHelper.startPage(page, 10);
        List<Ticket> ticketList = ticketService.search(checkin, checkout, startTime, theNextDay(startTime));
        PageInfo<Ticket> p=new PageInfo<>(ticketList);
//        request.setAttribute("items", items);
//        JSONArray ticketInfo = new JSONArray();
//        for(Ticket ticketObject : ticketList){
//            JSONObject jo = new JSONObject();
////            jo.put("id", ticketObject.getId());
//            jo.put("trainNumber", ticketObject.getTrainNumber());
//            jo.put("checkin", ticketObject.getCheckin());
//            jo.put("checkout", ticketObject.getCheckout());
//            jo.put("startTime", ticketObject.getStartTime());
//            jo.put("endTime", ticketObject.getEndTime());
//            jo.put("seatType", ticketObject.getSeatType());
//            jo.put("seatNumber", ticketObject.getSeatNumber());
//            jo.put("price", ticketObject.getPrice());
//            jo.put("amount", ticketObject.getAmount());
//            ticketInfo.put(jo);
//        }
//        model.addAttribute("page", p);
//        model.addAttribute("ticketList",ticketList);
        //for(Ticket ticket : items)
        ObjectMapper objectMapper = new ObjectMapper();
        String list4Json = null;
        try{
            list4Json = objectMapper.writeValueAsString(ticketList);
        }catch (JsonGenerationException e){
            e.printStackTrace();
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        request.setAttribute("ticketList", list4Json);
        return list4Json;
    }
    private Date theNextDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        return c.getTime();
    }

    //关键：将前端input type date类型时间格式化为"yyyy-MM-dd 00:00:00"转给后端Date类型
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    private int getCurrentPage(HttpServletRequest request) {
        String value = request.getParameter("currentPage");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }

}
