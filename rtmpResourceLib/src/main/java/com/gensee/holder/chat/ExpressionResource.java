package com.gensee.holder.chat;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.gensee.chat.gif.AbsChatResource;
import com.gensee.rtmpresourcelib.R;

public class ExpressionResource extends AbsChatResource{
	
	
	public static void initExpressionResource(Context context) {
		new ExpressionResource().initResource(context);
	}

	@Override
	protected void onInitTextTips(Context context, List<String> textTipList) {
		textTipList.add(getString(context, R.string.brow_tkl_cn));
		textTipList.add(getString(context, R.string.brow_tml_cn));
		textTipList.add(getString(context, R.string.brow_zt_cn));
		textTipList.add(getString(context, R.string.brow_fd_cn));
		textTipList.add(getString(context, R.string.brow_gz_cn));
		textTipList.add(getString(context, R.string.brow_zdsk_cn));
	}

	@Override
	protected void onGenseeBrowInit(List<Item> items) {
		items.add(new Item(R.string.brow_nh_cn, R.string.brow_nh_cn_text,R.drawable.brow_nh, "emotion.smile.gif"));
		items.add(new Item(R.string.brow_zj_cn, R.string.brow_zj_cn_text,R.drawable.brow_zj, "emotion.goodbye.gif"));
		items.add(new Item(R.string.brow_gx_cn, R.string.brow_gx_cn_text,R.drawable.brow_gx, "emotion.laugh.gif"));
		items.add(new Item(R.string.brow_sx_cn, R.string.brow_sx_cn_text,R.drawable.brow_sx, "emotion.cry.gif"));
		items.add(new Item(R.string.brow_fn_cn, R.string.brow_fn_cn_text,R.drawable.brow_fn, "emotion.angerly.gif"));
		items.add(new Item(R.string.brow_wl_cn, R.string.brow_wl_cn_text,R.drawable.brow_wl, "emotion.nod.gif"));
		items.add(new Item(R.string.brow_lh_cn, R.string.brow_lh_cn_text,R.drawable.brow_lh, "emotion.lh.gif"));
		items.add(new Item(R.string.brow_yw_cn, R.string.brow_yw_cn_text,R.drawable.brow_yw, "emotion.question.gif"));
		items.add(new Item(R.string.brow_bs_cn, R.string.brow_bs_cn_text,R.drawable.brow_bs, "emotion.bs.gif"));
		items.add(new Item(R.string.brow_xh_cn, R.string.brow_xh_cn_text,R.drawable.brow_xh, "rose.up.png"));
		items.add(new Item(R.string.brow_dx_cn, R.string.brow_dx_cn_text,R.drawable.brow_dx, "rose.down.png"));
		items.add(new Item(R.string.brow_lw_cn, R.string.brow_lw_cn_text,R.drawable.brow_lw, "chat.gift.png"));
		items.add(new Item(R.string.brow_tkl_cn, R.string.brow_tkl_cn_text,R.drawable.brow_tkl, "feedback.quickly.png"));
		items.add(new Item(R.string.brow_tml_cn, R.string.brow_tml_cn_text,R.drawable.brow_tml, "feedback.slowly.png"));
		items.add(new Item(R.string.brow_zt_cn, R.string.brow_zt_cn_text,R.drawable.brow_zt, "feedback.agreed.png"));
		items.add(new Item(R.string.brow_fd_cn, R.string.brow_fd_cn_text,R.drawable.brow_fd, "feedback.against.gif"));
		items.add(new Item(R.string.brow_gz_cn, R.string.brow_gz_cn_text,R.drawable.brow_gz, "feedback.applaud.png"));
		items.add(new Item(R.string.brow_zdsk_cn, R.string.brow_zdsk_cn_text,R.drawable.brow_zdsk, "feedback.think.png"));
		
		items.add(new Item(R.string.emotion_bz_cn, R.string.emotion_bz_cn_text,R.drawable.emotion_bz, "emotion.bz.gif"));
		items.add(new Item(R.string.emotion_fd_cn, R.string.emotion_fd_cn_text,R.drawable.emotion_fd, "emotion.fd.gif"));
		items.add(new Item(R.string.emotion_gg_cn, R.string.emotion_gg_cn_text,R.drawable.emotion_gg, "emotion.gg.gif"));
		items.add(new Item(R.string.emotion_gz_cn, R.string.emotion_gz_cn_text,R.drawable.emotion_gz, "emotion.gz.gif"));
		items.add(new Item(R.string.emotion_hx_cn, R.string.emotion_hx_cn_text,R.drawable.emotion_hx, "emotion.hx.gif"));
		items.add(new Item(R.string.emotion_jk_cn, R.string.emotion_jk_cn_text,R.drawable.emotion_jk, "emotion.jk.gif"));
		items.add(new Item(R.string.emotion_jy_cn, R.string.emotion_jy_cn_text,R.drawable.emotion_jy, "emotion.jy.gif"));
		items.add(new Item(R.string.emotion_kb_cn, R.string.emotion_kb_cn_text,R.drawable.emotion_kb, "emotion.kb.gif"));
		items.add(new Item(R.string.emotion_kl_cn, R.string.emotion_kl_cn_text,R.drawable.emotion_kl, "emotion.kl.gif"));
		items.add(new Item(R.string.emotion_ll_cn, R.string.emotion_ll_cn_text,R.drawable.emotion_ll, "emotion.ll.gif"));
		items.add(new Item(R.string.emotion_qd_cn, R.string.emotion_qd_cn_text,R.drawable.emotion_qd, "emotion.qd.gif"));
		items.add(new Item(R.string.emotion_qh_cn, R.string.emotion_qh_cn_text,R.drawable.emotion_qh, "emotion.qh.gif"));
		items.add(new Item(R.string.emotion_qq_cn, R.string.emotion_qq_cn_text,R.drawable.emotion_qq, "emotion.qq.gif"));
		items.add(new Item(R.string.emotion_rb_cn, R.string.emotion_rb_cn_text,R.drawable.emotion_rb, "emotion.rb.gif"));
		items.add(new Item(R.string.emotion_se_cn, R.string.emotion_se_cn_text,R.drawable.emotion_se, "emotion.se.gif"));
		items.add(new Item(R.string.emotion_tx_cn, R.string.emotion_tx_cn_text,R.drawable.emotion_tx, "emotion.tx.gif"));
		items.add(new Item(R.string.emotion_xu_cn, R.string.emotion_xu_cn_text,R.drawable.emotion_xu, "emotion.xu.gif"));
		items.add(new Item(R.string.emotion_yun_cn, R.string.emotion_yun_cn_text,R.drawable.emotion_yun, "emotion.yun.gif"));	
	}


	/**
	 * 扩展第三方表情
	 * @param key      string 中作为映射Key的资源  只要英文
	 * @param textId   string 中纯文本中表情文字   可以有多语言              
	 * @param drawale  drawable 中表情图片资源                   
	 * @param path     富文本中图片路径（完整名称 xxx.xx.png）extra.xxx.png
	 * Item(int key, int textId, int drawale, String path)
	 * 
	 * step 1、添加图片haoche.png资源到drawable
	 * step 2、string 添加表情纯文本的文字表示<string name="extra_haoche_text">【豪车】</string>
	 *         string 添加映射的key           <string name="extra_haoche_key">【extra.haoche.png】</string>
	 * step 3、items.add(new Item(R.string.extra_haoche_key, R.string.extra_haoche_text,R.drawable.haoche, "extra.haoche.png"))        
	 */
	@Override
	protected void onExtraBrowInit(List<Item> items) {
		// TODO extra brow
	}
	
}
