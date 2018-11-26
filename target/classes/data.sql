INSERT INTO log(date,ip,request,status,userAgent )VALUES
 ('2017-01-01 00:12:53.534','23', 'TEST', '2312', 'MAC');

select ip, count(*) as total
from log where date >= '2017-01-01 00:00:12' and date <= '2017-01-01 00:06:02' group by ip having total > 5