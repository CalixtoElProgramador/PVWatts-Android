package com.example.pvwatts.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pvwatts.core.BaseViewHolder
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.databinding.ItemProjectsBinding

class ProjectAdapter(
    private val projectList: List<Project>,
    private val itemClickListener: OnProjectClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnProjectClickListener {
        fun onProjectClick(project: Project)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemProjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ProjectViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onProjectClick(projectList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ProjectViewHolder -> holder.bind(projectList[position])
        }
    }

    override fun getItemCount(): Int = projectList.size

    private inner class ProjectViewHolder(
        val binding: ItemProjectsBinding,
        val context: Context
    ) : BaseViewHolder<Project>(binding.root) {
        override fun bind(item: Project) {
            Glide.with(context).load(item.image_url).centerCrop().into(binding.kbvLocation)
            binding.textTitleCard.text = item.name
            binding.textDateCreation.text = item.created_at
            binding.textLocation.text = item.location
        }
    }

}