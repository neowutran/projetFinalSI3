package model;

import com.google.gson.annotations.Expose;
import model.person.Borrower;

import java.util.Calendar;
import java.util.List;

/**
 * Created by neowutran on 24/01/14.
 */
public class Log {


        @Expose
        private Logs.Type type;
        @Expose
        private String userId;
        @Expose
        private String equipment;
        @Expose
        private String borrow;
        @Expose
        private Long calendar;
        @Expose
        private String person;
        @Expose
        private String infos;

        public Log(Logs.Type type, Equipment equipment, Borrower.Borrow borrow, Person person, String info){

            this.type = type;
            if(User.getInstance().getPersonId() == null) return;
            this.userId = User.getInstance().getPersonId();
            if(equipment != null){
            this.equipment = equipment.getId();
            }
            if(borrow != null){
            this.borrow = borrow.getId();
            }

            this.calendar = Calendar.getInstance().getTimeInMillis();
            if(person != null){
            this.person = person.getId();
            }
            this.infos = info;
            List<Log> logs = Logs.getInstance().getLogs();
            logs.add(this);
            Logs.getInstance().setLogs(logs);

        }

        public Log(Logs.Type type, String equipment, String borrow, String person, String info, String userId, Long calendar){
            this.type = type;
            this.userId = userId;
            this.equipment = equipment;
            this.borrow = borrow;
            this.calendar = calendar;
            this.person = person;
            this.infos = info;
            List<Log> logs = Logs.getInstance().getLogs();
            logs.add(this);
            Logs.getInstance().setLogs(logs);
        }


}
