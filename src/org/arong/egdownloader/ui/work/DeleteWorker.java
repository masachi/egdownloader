package org.arong.egdownloader.ui.work;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import org.arong.egdownloader.model.Picture;
import org.arong.egdownloader.model.Task;
import org.arong.egdownloader.ui.table.TaskingTable;
import org.arong.egdownloader.ui.window.DeletingWindow;
import org.arong.egdownloader.ui.window.EgDownloaderWindow;
/**
 * 删除任务线程类
 * @author 阿荣
 * @since 2014-09-19
 */
public class DeleteWorker extends SwingWorker<Void, Void>{
	
	private JFrame window;
	private TaskingTable table;
	private DeletingWindow w;
	private int[] rows;
	public DeleteWorker(JFrame mainWindow, TaskingTable table, DeletingWindow w, int[] rows){
		this.window = mainWindow;
		this.table = table;
		this.rows = rows;
		this.w = w;
	}
	
	protected Void doInBackground() throws Exception {
		try{
			EgDownloaderWindow mainWindow = (EgDownloaderWindow)window;
			Task task;
			List<Picture> pics = new ArrayList<Picture>();
			List<Task> tasks = new ArrayList<Task>();
			for(int i = 0; i < rows.length; i ++){
				if(table.getTasks().size() >= (rows[i])){
					task = table.getTasks().get(rows[i]);
					tasks.add(task);
					w.setData((i + 1) + "/" + rows.length);
					w.setInfo("收集:" + task.getName());
					if(task.getPictures() != null && task.getPictures().size() > 0){
						pics.addAll(task.getPictures());
					}
				}
				
			}
			//操作数据库
			if(tasks.size() > 0){
				if(pics.size() > 0){
					w.setInfo("正在删除任务图片");
					mainWindow.pictureDbTemplate.delete(pics);//删除图片信息
				}
				w.setInfo("正在删除任务");
				mainWindow.taskDbTemplate.delete(tasks);//删除任务
				//更新内存
				table.getTasks().removeAll(tasks);
			}
			table.clearSelection();//使之不选中任何行
			table.updateUI();//刷新表格
			if(table.getTasks().size() == 0){
				mainWindow.tablePane.setVisible(false);//将任务panel隐藏
				mainWindow.emptyTableTips.setVisible(true);//将空任务label显示
			}
			w.dispose();
		}catch(Exception e){
			
		}	
		return null;
	}

}