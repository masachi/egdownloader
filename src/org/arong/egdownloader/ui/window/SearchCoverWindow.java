package org.arong.egdownloader.ui.window;

import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import org.arong.egdownloader.model.SearchTask;
import org.arong.egdownloader.ui.ComponentConst;
import org.arong.util.FileUtil;
/**
 * 搜索结果封面窗口
 * @author dipoo
 * @since 2015-03-13
 */
public class SearchCoverWindow extends JWindow {

	private static final long serialVersionUID = 6598148765450431311L;
	
	private SearchComicWindow comicWindow;
	
	private JLabel coverLabel = null;
	
	private ImageIcon icon;
	
	public SearchCoverWindow(SearchComicWindow comicWindow){
		this.comicWindow = comicWindow;
		coverLabel = new JLabel();
		this.setLayout(null);
		this.getContentPane().add(coverLabel);
	}
	
	public void showCover(SearchTask task, Point p){
		//检测封面是否已下载http://exhentai.org/g/794884/2278359e3a
		String path = ComponentConst.CACHE_PATH + "/" + FileUtil.filterDir(task.getUrl());
		File cover = new File(path);
		if(cover == null || !cover.exists()){
			this.setSize(16, 16);
			coverLabel.setSize(16, 16);
			coverLabel.setIcon(new ImageIcon(getClass().getResource(ComponentConst.ICON_PATH + "loading.gif")));
		}else{
			icon = new ImageIcon(path);
			if(icon.getIconWidth() == -1){
				this.setSize(16, 16);
				coverLabel.setSize(16, 16);
				coverLabel.setIcon(new ImageIcon(getClass().getResource(ComponentConst.ICON_PATH + "loading.gif")));
			}else{
				this.setSize(icon.getIconWidth(), icon.getIconHeight());
				coverLabel.setSize(icon.getIconWidth(), icon.getIconHeight());
				coverLabel.setIcon(icon);
			}
		}
		this.setLocationRelativeTo(comicWindow);
		this.setLocation((int)p.getX(), (int)p.getY() - this.getHeight() / 2 - 50);
		this.setVisible(true);
	}

}