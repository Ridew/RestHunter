package streaming.king.rest.service

import java.io.StringWriter

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

/**
  * @author xiaguobing
  * @version 2016-06-17
  **/
class Paginate(page: Int, pageSize: Int) {

  var _totalItems: Int = _

  def totalItems(ti: Int) = {
    _totalItems = ti
    this
  }

  def pageCalc = {
    val from = ((page - 1) * pageSize)
    val totalPages = (_totalItems / pageSize) + (if (_totalItems % pageSize > 0) 1 else 0)
    (from, totalPages)
  }

  def paginate = {
    val context = new VelocityContext()
    val pInfo = pageCalc
    context.put("haveNext", if (pInfo._2 > page) true else false)
    context.put("havePreview", if (page > 1) true else false)
    context.put("next", page + 1);
    context.put("preview", page - 1);
    context.put("pageSize", pageSize);
    val writer = new StringWriter();
    Velocity.mergeTemplate("/rest/common/paginate.vm", "utf-8", context, writer)
    writer.toString
  }

}
