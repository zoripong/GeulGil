package com.five.high.emirim.geulgil.Keep;

import java.util.ArrayList;

/**
 * Created by 두리 on 2017-07-26.
 */

//  없어질 클래스
public class MeanApi {

    private ArrayList<Channel> channelList=new ArrayList<Channel>();
    private int numofchannelList=-1;
    private int numofitemlList=-1;
    private int numofsenselList=-1;
    private String[] pos_str={"전체","명사","동사","형용사","부사"};
    private String[] cat_str={"전체","건설","경영","경제","수학"};
    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    public String key="73325502CDC413EE1BEBC05159093183"; //Naver 개발자센터 검색 키
    private StringBuffer mBuffer;
    private String mLocation;

//    public SelectMeanItem getXmlData(String str) {
//        String tag;
//        boolean[] pos_click = M.sPartCheck;
//        boolean[] cat_click = M.sCatCheck;
//
//        mBuffer = new StringBuffer();
//
//        mLocation = URLEncoder.encode(str); //한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
//        String queryUrl = "https://opendict.korean.go.kr/api/search"   //요청 URL
//                + "?key=" + key                        //key 값
//                + "&q=" + mLocation            //지역검색 요청
//                + "&num=100";
//
//        try {
//            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is = url.openStream();  //url위치로 입력스트림 연결
//
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser xpp = factory.newPullParser();
//            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기
//
//            int eventType = xpp.getEventType();
//            boolean inword = false, indefinition = false, insenseno = false, inpos = false, intype = false, incat = false;
//            //태그의 이름이 무엇이었는지 확인하는 변수
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT://xml 시작하면 channel 생성, channelList 인덱스 하나 증가, channel생성 시 itemList 인덱스, senseList 인덱스 초기화
//                        channelList.add(new Channel());
//                        numofchannelList++;
//                        numofitemlList = -1;
//                        numofsenselList = -1;
//                        break;
//
//                    case XmlPullParser.START_TAG://시작 태그 이름이 무엇인지 확인
//                        tag = xpp.getName(); //테그 이름 얻어오기
//
//                        if (tag.equals("item")) {//시작 태그 이름이 item이면 itemList에 Item 객체 하나 추가
//                            channelList.get(numofchannelList).itemList.add(new MeanApiItem());
//                            numofitemlList++;
//                        } else if (tag.equals("word")) {
//                            inword = true;
//                        } else if (tag.equals("sense")) {//시작 태그 이름이 sense이면 senseList에 Sense 객체 하나 추가
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.add(new Sense());
//                            numofsenselList++;
//                        } else if (tag.equals("sense_no")) {
//                            insenseno = true;
//                        } else if (tag.equals("definition")) {
//                            indefinition = true;
//                        } else if (tag.equals("pos")) {
//                            inpos = true;
//                        } else if (tag.equals("type")) {
//                            intype = true;
//                        } else if (tag.equals("cat")) {
//                            incat = true;
//                        }
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        if (inword) {//channelList의 해당 인덱스 방의 channel의 itemlList의 해당 인덱스 방의 item의 word에
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).setWord(URLEncoder.encode(xpp.getText()));
//                            inword = false;
//                        } else if (insenseno) {
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).setSense_no(xpp.getText());
//                            insenseno = false;
//                        } else if (indefinition) {//channelList의 해당 인덱스 방의 channel의 itemlList의 해당 인덱스 방의 item의 senseList의 해당 인덱스 방의 sense의 definition에
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).setDefinition(xpp.getText());
//                            indefinition = false;
//                        } else if (inpos) {
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).setPos(xpp.getText());
//                            inpos = false;
//                        } else if (intype) {
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).setType(xpp.getText());
//                            intype = false;
//                        } else if (incat) {
//                            channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).setCat(xpp.getText());
//                            incat = false;
//                        }
//                        break;
//
//                    case XmlPullParser.END_DOCUMENT:
//                        break;
//
//                    case XmlPullParser.END_TAG://끝 태그
//                        tag = xpp.getName();    //테그 이름 얻어오기
////                        buffer.append("</"+tag+">");
//                        if (tag.equals("item")) {
//                            numofsenselList = -1;
//                        }
//                        if (tag.equals("sense")) {
//                            String pos = channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).getPos();
//                            String cat = channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).getCat();
//
//                            for (int i = 0; i < pos_click.length; i++) {
//                                if (pos == null) {
//                                    if (pos_click[0]) {
//                                        channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).choice++;
//                                        break;
//                                    }
//                                } else {
//                                    if (pos_click[0] || (pos_click[i] && pos.equals(pos_str[i]))) {
//                                        channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).choice++;
//                                        break;
//                                    }
//                                }
//                            }
//                            for (int j = 0; j < cat_click.length; j++) {
//                                if (cat == null) {
//                                    if (cat_click[0]) {
//                                        channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).choice++;
//                                        break;
//                                    }
//                                } else {
//                                    if (cat_click[0] || (cat_click[j] && cat.equals(cat_str[j]))) {
//                                        channelList.get(numofchannelList).itemList.get(numofitemlList).senseList.get(numofsenselList).choice++;
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        break;
//                }
//                eventType = xpp.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        //// TODO: 2017-08-06 동음이의어 대기
////        if(channelList.get(numofchannelList).itemList.get(i).senseList.size() >= 2){
////            사용자가 선택 할 수 있도록 진행
////          "단어-INDEX"
////        }else{
////          "단어-0"
////        }
//
//
//
////        return new SelectMeanItem(word. mean, category, part); // 각 자리에 적당한 값으로 넣어줘!
//        return null; // 에러 막을려고 해논거니까 할 때는 삭제하고 사용~
//    }//getXmlData method....
//

    // // TODO: 2017-08-06  클래스 완성 후 필요 없을 경우 삭제!
//    private void appendResult(){
//        for(int i=0;i<=numofitemlList;i++) {//word가 location과 같으면 pos와 cat 체크박스를 확인해서 둘다 만족하는 sense의 정의 버퍼에 넣기
//            for (int j = 0; j < channelList.get(numofchannelList).itemList.get(i).senseList.size(); j++) {
//                if (channelList.get(numofchannelList).itemList.get(i).getWord().equals(mLocation)) {//word가 사용자가 입력한 값과 같으면
////                    buffer.append(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).getDefinition() + "\n");
//                    if(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).choice>=2){
//                        mBuffer.append(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).getPos() + "\t");
//                        mBuffer.append(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).getCat() + "\t");
////                        mBuffer.append(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).choice+ "\n");
//                        mBuffer.append(channelList.get(numofchannelList).itemList.get(i).senseList.get(j).getDefinition() + "\t");
//                    }
//
//                }
//
//            }
//        }
//    }
}
